package com.hrms.careerpathmanagement.services.impl;

import com.hrms.careerpathmanagement.dto.*;
import com.hrms.careerpathmanagement.models.*;
import com.hrms.careerpathmanagement.repositories.*;
import com.hrms.careerpathmanagement.services.CompetencyService;
import com.hrms.careerpathmanagement.specification.CareerSpecification;
import com.hrms.careerpathmanagement.specification.CompetencySpecification;
import com.hrms.employeemanagement.models.*;
import com.hrms.employeemanagement.specification.EmployeeSpecification;
import com.hrms.global.paging.Pagination;
import com.hrms.employeemanagement.repositories.*;
import com.hrms.employeemanagement.services.EmployeeManagementService;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.hrms.global.paging.PaginationSetup.setupPaging;

@Service
@Transactional
public class CompetencyServiceImpl implements CompetencyService {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private CompetencyEvaluationRepository competencyEvaluationRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompetencyTimeLineRepository competencyTimeLineRepository;
    @Autowired
    private SkillSetEvaluationRepository skillSetEvaluationRepository;
    @Autowired
    private SkillSetTargetRepository skillSetTargetRepository;
    @Autowired
    private PositionSkillSetRepository positionSkillSetRepository;
    @Autowired
    private CompetencyRepository competencyRepository;
    @Autowired
    private CompetencyCycleRepository competencyCycleRepository;
    @Autowired
    private ProficiencyLevelRepository proficiencyLevelRepository;
    @Autowired
    private EvaluationOverallRepository evaluationOverallRepository;
    @Autowired
    private SkillSetRepository skillSetRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeManagementService employeeManagementService;
    @Autowired
    JobLevelRepository jobLevelRepository;
    @Autowired
    PositionJobLevelSkillSetRepository positionLevelSkillSetRepository;

    @Autowired
    CareerSpecification careerSpecification;
    @Autowired
    EmployeeSpecification employeeSpecification;
    @Autowired
    CompetencySpecification competencySpecification;

    private CompetencyCycle latestCycle;

    @PostConstruct
    private void initialize() {
        this.latestCycle = getLatestCycle();
    }

    private CompetencyCycle getLatestCycle() {
        return competencyCycleRepository.findFirstByOrderByStartDateDesc();
    }

    static String SELF_EVALUATION = "Self Evaluation";
    static String SUPERVIOR = "Supervisor";
    static String FINALSCORE = "Final Score";

    public List<SkillSetEvaluation> getSkillEvaluations(Integer employeeId, Integer cycleId) {
        Specification<SkillSetEvaluation> empSpec = employeeSpecification.hasEmployeeId(employeeId);
        return skillSetEvaluationRepository.findAll(empSpec.and(getCycleSpec(cycleId)));
    }

    @NotNull
    private static <T> Specification<T> getCycleSpec(Integer cycleId) {
        return (root, query, builder) -> builder.equal(root.get("competencyCycle").get("id"), cycleId);
    }

    //TODO: TARGET SKILL SET -> FROM HR SET (BASE ON POSITION & JOB LEVEL)
    public List<SkillSet> getBaselineSkillsSet(Integer positionId, Integer levelId) {
        Specification<PositionJobLevelSkillSet> posSpec = careerSpecification.hasPositionId(positionId);
        Specification<PositionJobLevelSkillSet> levelSpec = careerSpecification.hasJobLevelId(levelId);
        return positionLevelSkillSetRepository.findAll(posSpec.and(levelSpec))
                .stream()
                .map(PositionJobLevelSkillSet::getSkillSet)
                .toList();  //Have not optimized yet
    }

    public List<SkillSet> getTargetSkillsSet(Integer employeeId, Integer cycleId) {
        Specification<SkillSetTarget> empSpec = employeeSpecification.hasEmployeeId(employeeId);
        Specification<SkillSetTarget> cycSpec = competencySpecification.hasCycleId(cycleId);

        return skillSetTargetRepository.findAll(empSpec.and(cycSpec))
                .stream().map(SkillSetTarget::getSkillSet).toList();
    }

    public List<CompetencyEvaluation> getCompetencyEvaluations(Integer employeeId, Integer cycleId) {
        Specification<CompetencyEvaluation> empSpec = employeeSpecification.hasEmployeeId(employeeId);
        Specification<CompetencyEvaluation> cycleSpec = competencySpecification.hasCycleId(cycleId);
        return competencyEvaluationRepository.findAll(empSpec.and(cycleSpec));
    }

    /**
     * if skillEval is null, currentScore will be null
     * if targetSkill is null, targetScore will be null
     *
     * @return SkillSummarization (DTO)
     */
    public SkillSetSummarizationDTO getSkillSummarization(Integer employeeId, Integer cycleId) {
        //TODO: SQL GROUP BY SKILL SET AND GET AVG OF ALL SKILLS -- DONE
        //1. Skill Set Average Score
        var skillSetAvgScore = getAverageSkillSet(employeeId, cycleId);

        //2. Skill Set Target Score
        var positionLevel = getPositionLevel(employeeId);
        var skillSetBaselineScore = getBaselineSkillSetScore(positionLevel.positionId(), positionLevel.jobLevelId());

        return new SkillSetSummarizationDTO(skillSetAvgScore, skillSetBaselineScore);
    }

    public Optional<Double> getAverageSkillSet(Integer empId, Integer cycleId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Double> query = cb.createQuery(Double.class);
        Root<SkillSetEvaluation> root = query.from(SkillSetEvaluation.class);
        Join<SkillSetEvaluation, ProficiencyLevel> proficencyJoin = root.join("employeeProficiencyLevel");

        query.select(cb.avg(proficencyJoin.get("score")));
        query.where(cb.equal(root.get("employee").get("id"), empId),
                cb.equal(root.get("competencyCycle").get("id"), cycleId));

        return Optional.ofNullable(entityManager.createQuery(query).getSingleResult());
    }

    public Optional<Double> getBaselineSkillSetScore(Integer positionId, Integer levelId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Double> query = cb.createQuery(Double.class);
        Root<PositionJobLevelSkillSet> root = query.from(PositionJobLevelSkillSet.class);
        Join<PositionJobLevelSkillSet, ProficiencyLevel> proficencyJoin = root.join("proficiencyLevel");

        query.select(cb.avg(proficencyJoin.get("score")));
        query.where(cb.equal(root.get("position").get("id"), positionId),
                cb.equal(root.get("jobLevel").get("id"), levelId));

        return Optional.ofNullable(entityManager.createQuery(query).getSingleResult());
    }

    private PositionLevelDTO getPositionLevel(Integer empId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PositionLevelDTO> query = cb.createQuery(PositionLevelDTO.class);
        Root<Employee> root = query.from(Employee.class);
        Join<Employee, Position> positionJoin = root.join("position");
        Join<Employee, JobLevel> jobLevelJoin = root.join("jobLevel");

        query.multiselect(positionJoin.get("id"), jobLevelJoin.get("id"));
        query.where(cb.equal(root.get("id"), empId));

        return entityManager.createQuery(query).getSingleResult();
    }


    //TODO: Schedule service
    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void updateIsDoneForOverdueItems() {
        competencyTimeLineRepository.updateIsDoneForOverdueItems();
    }

    @Override
    public List<CompetencyTimeLine> getCompetencyTimeline(Integer competencyCycleId) {
        Specification<CompetencyTimeLine> spec =
                (root, query, cb) -> cb.equal(root.get("competencyCycle").get("id"), competencyCycleId);
        return competencyTimeLineRepository.findAll(spec);
    }

    @Override
    public List<DepartmentInCompleteDTO> getDepartmentIncompletePercent(Integer competencyCycleId) {
        List<Integer> departmentIds = departmentRepository
                .findAll()
                .stream()
                .map(Department::getId)
                .toList();

        //for each all departments
        return departmentIds.stream().map(item -> {
            //get all employees of each department
            List<Integer> empIdSet = employeeManagementService
                    .findEmployees(item)
                    .stream()
                    .map(Employee::getId)
                    .toList();

            float employeePercent = getEmployeeIncompletedPercent(competencyCycleId, empIdSet);
            float evaluatorPercent = getEvaluatorInCompletePercent(competencyCycleId, empIdSet);

            return new DepartmentInCompleteDTO(item, employeePercent, evaluatorPercent);
        }).toList();
    }

    private float getEvaluatorInCompletePercent(Integer competencyCycleId, List<Integer> empIdSet) {
        //get all employees who have completed evaluator-evaluation
        Specification<CompetencyEvaluationOverall> specCompleteEvaluator = (root, query, criteriaBuilder)
                -> criteriaBuilder.and(
                root.get("employee").get("id").in(empIdSet),
                criteriaBuilder.equal(root.get("evaluatorStatus"), "Completed"),
                criteriaBuilder.equal(root.get("competencyCycle").get("id"), competencyCycleId));

        var evaluatorHasCompleted = evaluationOverallRepository.count(specCompleteEvaluator);
        var evaluatorHasInCompleted = empIdSet.size() - evaluatorHasCompleted;
        return (float) evaluatorHasInCompleted / empIdSet.size() * 100;
    }

    private float getEmployeeIncompletedPercent(Integer competencyCycleId, List<Integer> empIdSet) {
        //get all employees who have completed self-evaluation
        Specification<CompetencyEvaluationOverall> specCompleteEval = (root, query, criteriaBuilder)
                -> criteriaBuilder.and(
                root.get("employee").get("id").in(empIdSet),
                criteriaBuilder.equal(root.get("employeeStatus"), "Completed"),
                criteriaBuilder.equal(root.get("competencyCycle").get("id"), competencyCycleId));

        var employeesHasCompleted = evaluationOverallRepository.count(specCompleteEval);
        var employeesHasInCompleted = empIdSet.size() - employeesHasCompleted;
        return (float) employeesHasInCompleted / empIdSet.size() * 100;
    }

    @Override
    public List<CompanyIncompletedDTO> getCompanyIncompletePercent(Integer competencyCycleId) {
        List<CompanyIncompletedDTO> companyEvaPercents = new ArrayList<>();
        List<Integer> empIdSet = employeeRepository
                .findAll()
                .stream()
                .map(Employee::getId)
                .toList();

        //get all employees who have completed evaluation
        Specification<CompetencyEvaluationOverall> spec = (root, query, criteriaBuilder) -> criteriaBuilder.and(
                root.get("employee").get("id").in(empIdSet),
                criteriaBuilder.equal(root.get("finalStatus"), "Agreed"),
                criteriaBuilder.equal(root.get("competencyCycle").get("id"), competencyCycleId)
        );

        var hasCompletedPercent = (float) evaluationOverallRepository.count(spec) / empIdSet.size() * 100;
        var hasInCompleted = 100 - hasCompletedPercent;

        companyEvaPercents.add(new CompanyIncompletedDTO("Completed", hasCompletedPercent));
        companyEvaPercents.add(new CompanyIncompletedDTO("InCompleted", hasInCompleted));
        return companyEvaPercents;
    }



    @Override
    public List<AvgCompetencyDTO> getAvgCompetencies(Integer positionId, Integer competencyCycleId) {
//        List<CompetencyEvaluation> compEvaluates = positionId != null
//                ? findByPositionAndCycle(positionId, competencyCycleId)
//                : findByCycle(competencyCycleId);
//        List<Integer> jobLevelIds = jobLevelRepository
//                .findAll()
//                .stream()
//                .map(JobLevel::getId)
//                .toList();
//        List<Integer> competencyIds = competencyRepository
//                .findAll()
//                .stream().
//                map(Competency::getId)
//                .toList();
//
//        //Join 2 list using flatMap and store it by Pair
//        List<Pair<Integer, Integer>> pairItems = jobLevelIds.stream()
//                .flatMap(jobLevel -> competencyIds
//                        .stream()
//                        .map(competency -> new Pair<>(jobLevel, competency)))
//                .toList();
//
//        return pairItems.stream().map(pair -> {
//            var jobLevel = pair.getFirst();
//            var competency = pair.getSecond();
//            List<CompetencyEvaluation> evaluationsHasJobLevelAndCompetency = compEvaluates.stream()
//                    .filter(compEva -> compEva.getEmployee().getJobLevel().getId() == jobLevel
//                            && compEva.getCompetency().getId().equals(competency))
//                    .toList();
//            float avgScore = evaluationsHasJobLevelAndCompetency.isEmpty() ? 0
//                    : (float) evaluationsHasJobLevelAndCompetency.stream()
//                    .map(CompetencyEvaluation::getProficiencyLevel)
//                    .filter(Objects::nonNull)
//                    .mapToInt(ProficiencyLevel::getScore)
//                    .average()
//                    .orElse(0);
//            return new AvgCompetencyDTO(jobLevel, competency, avgScore);
//        }).toList();
        return null;
    }

    @Override
    public RadarChartDTO getCompetencyRadarChart(List<Integer> competencyCyclesId, Integer departmentId) {
//        var competencyIds = competencyRepository.findAll().stream().map(Competency::getId).toList();
//        var competencyEvaluates = findByCyclesAndDepartment(competencyCyclesId, departmentId);
//        proficiencyLevelRepository.findAll();
//
//        //Join 2 list using flatMap and store it by Pair
//        List<Pair<Integer, Integer>> pairItems = competencyCyclesId.stream()
//                .flatMap(cycle -> competencyIds
//                        .stream()
//                        .map(competency -> new Pair<>(cycle, competency)))
//                .toList();
//
//        //Get label for each dataset and RadarDatasetDTO have sture list of score RadarDatasetDTO(competencyCycleName, listScore)
//        List<RadarValueDTO> avgCompetencies = pairItems.stream().map(pair -> {
//            var cycle = pair.getFirst();
//            var competency = pair.getSecond();
//            List<CompetencyEvaluation> compEvaluate = competencyEvaluates.stream()
//                    .filter(compEva -> compEva.getCompetencyCycle().getId() == cycle
//                            && compEva.getCompetency().getId().equals(competency))
//                    .toList();
//            float avgScore = compEvaluate.isEmpty() ? 0
//                    : (float) compEvaluate.stream()
//                    .map(CompetencyEvaluation::getProficiencyLevel)
//                    .filter(Objects::nonNull)
//                    .mapToInt(ProficiencyLevel::getScore)
//                    .average()
//                    .orElse(0);
//            return new RadarValueDTO(cycle, competency, avgScore);
//        }).toList();
//
//        List<RadarDatasetDTO> listDataset = competencyCyclesId.stream().map(cycle -> {
//            List<Float> listScore = competencyIds
//                    .stream()
//                    .map(competency -> avgCompetencies.stream().filter(avgCompetency ->
//                                    avgCompetency.getCompetencyId().equals(competency) &&
//                                            avgCompetency.getCompetencyCycleId().equals(cycle))
//                            .findFirst()
//                            .map(RadarValueDTO::getAverage).orElse(null)).toList();
//            return new RadarDatasetDTO(competencyCycleRepository
//                    .findAll()
//                    .stream()
//                    .filter(competencyCycle -> competencyCycle.getId() == cycle)
//                    .findFirst()
//                    .orElseThrow()
//                    .getCompetencyCycleName(), listScore);
//        }).toList();
//
//        //Get list labels using spec and return RadarChartDTO
//        Specification<Competency> specComp = (root, query, criteriaBuilder) -> root.get("id").in(competencyIds);
//        List<String> labels = competencyRepository
//                .findAll(specComp)
//                .stream()
//                .map(Competency::getCompetencyName)
//                .toList();
//        return new RadarChartDTO(labels, listDataset);
        return null;
    }

    @Override
    public SkillSetPagingDTO getHighestSkillSet(@Nullable Integer employeeId,
                                                Integer competencyCycleId, int pageNo, int pageSize) {
        CompetencyCycle evalLatestCycle = evaluationOverallRepository.latestEvalCompetencyCycle(employeeId);
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        skillSetRepository.findAll();
        proficiencyLevelRepository.findAll();

        Specification<SkillSetEvaluation> spec = employeeId == null
                ?
                (root, query, builder) -> {
                    query.orderBy(builder.desc(root.get("finalProficiencyLevel")));
                    query.where(builder.equal(root.get("competencyCycle").get("id"), competencyCycleId));
                    return query.getRestriction();
                }
                :
                (root, query, builder) -> {
                    query.orderBy(builder.desc(root.get("finalProficiencyLevel")));
                    query.where(builder.equal(root.get("competencyCycle").get("id"), evalLatestCycle.getId()),
                            builder.equal(root.get("employee").get("id"), employeeId));
                    return query.getRestriction();
                };

        Page<SkillSetDTO> ssEvaluates = skillSetEvaluationRepository
                .findAll(spec, pageable)
                .map(item -> new SkillSetDTO(item.getEmployee().getId(),
                        item.getSkillSet().getSkillSetName(),
                        item.getFinalProficiencyLevel().getScore()));
        Pagination pagination = setupPaging(ssEvaluates.getTotalElements(), pageNo, pageSize);
        return new SkillSetPagingDTO(ssEvaluates.getContent(), pagination);
    }

    @Override
    public SkillSetPagingDTO getTopKeenSkillSetEmployee(Integer employeeId, int pageNo, int pageSize) {
        CompetencyCycle evalLatestCycle = evaluationOverallRepository.latestEvalCompetencyCycle(employeeId);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SkillSetDTO> criteriaQuery = criteriaBuilder.createQuery(SkillSetDTO.class);

        Root<SkillSetEvaluation> sseRoot = criteriaQuery.from(SkillSetEvaluation.class);
        Join<SkillSetEvaluation, ProficiencyLevel> plJoin = sseRoot.join("finalProficiencyLevel");
        Join<SkillSetEvaluation, SkillSet> ssJoin = sseRoot.join("skillSet");
        Join<SkillSetEvaluation, Employee> eJoin = sseRoot.join("employee");

        criteriaQuery.multiselect(
                eJoin.alias("e"),
                ssJoin.alias("ss"),
                plJoin.alias("pl")
        );

        criteriaQuery.where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(sseRoot.get("competencyCycle").get("id"), evalLatestCycle.getId()),
                        criteriaBuilder.equal(sseRoot.get("employee").get("id"), employeeId)
                )
        );

        criteriaQuery.orderBy(criteriaBuilder.asc(plJoin.get("score")));

        TypedQuery<SkillSetDTO> query = entityManager.createQuery(criteriaQuery);
        List<SkillSetDTO> results = query.getResultList();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<SkillSetDTO> topsHighest = new PageImpl<>(results, pageable, results.size());
        Pagination pagination = setupPaging(topsHighest.getTotalElements(), pageNo, pageSize);
        return new SkillSetPagingDTO(topsHighest.getContent(), pagination);
    }

    @Override
    public SkillSetPagingDTO getTopHighestSkillSetTargetEmployee(Integer employeeId, int pageNo, int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SkillSetDTO> criteriaQuery = criteriaBuilder.createQuery(SkillSetDTO.class);

        Root<SkillSetTarget> sseRoot = criteriaQuery.from(SkillSetTarget.class);
        Join<SkillSetTarget, ProficiencyLevel> plJoin = sseRoot.join("targetProficiencyLevel");
        Join<SkillSetTarget, SkillSet> ssJoin = sseRoot.join("skillSet");
        Join<SkillSetTarget, Employee> eJoin = sseRoot.join("employee");

        criteriaQuery.multiselect(
                eJoin.get("id"),
                ssJoin.get("skillSetName"),
                plJoin.get("score")
        );

        criteriaQuery.where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(sseRoot.get("competencyCycle").get("id"), latestCycle.getId()),
                        criteriaBuilder.equal(sseRoot.get("employee").get("id"), employeeId)
                )
        );

        criteriaQuery.orderBy(criteriaBuilder.asc(plJoin.get("score")));

        TypedQuery<SkillSetDTO> query = entityManager.createQuery(criteriaQuery);
        List<SkillSetDTO> results = query.getResultList();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<SkillSetDTO> topsHighest = new PageImpl<>(results, pageable, results.size());
        Pagination pagination = setupPaging(topsHighest.getTotalElements(), pageNo, pageSize);
        return new SkillSetPagingDTO(topsHighest.getContent(), pagination);
    }

    @Override
    public CurrentEvaluationDTO getCurrentEvaluation(Integer employeeId) {
        Specification<CompetencyEvaluationOverall> spec = (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("employee").get("id"), employeeId),
                criteriaBuilder.equal(root.get("competencyCycle").get("id"), latestCycle.getId())
        );
        CompetencyEvaluationOverall evalOvr = evaluationOverallRepository.findOne(spec).orElse(null);
        return evalOvr == null
                ? new CurrentEvaluationDTO(latestCycle.getCompetencyCycleName(), "Not Started", null)
                : new CurrentEvaluationDTO(evalOvr.getCompetencyCycle().getCompetencyCycleName(),
                                           evalOvr.getFinalStatus(), evalOvr.getLastUpdated().toString());
    }

    @Override
    public List<HistoryEvaluationDTO> getHistoryEvaluations(Integer employeeId) {
        List<CompetencyCycle> compCycles = competencyCycleRepository.findAll();
        List<Integer> cycleIds = compCycles.stream().map(CompetencyCycle::getId).toList();
        Specification<CompetencyEvaluationOverall> spec = (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("employee").get("id"), employeeId),
                root.get("competencyCycle").get("id").in(cycleIds)
        );

        return evaluationOverallRepository
                .findAll(spec)
                .stream()
                .map(evalOvr -> new HistoryEvaluationDTO(evalOvr.getCompletedDate().toString(),
                        evalOvr.getCompetencyCycle().getCompetencyCycleName(),
                        evalOvr.getFinalStatus(), evalOvr.getScore()))
                .toList();
    }


    @Override
    public CompanyCompetencyDiffPercentDTO getCompanyCompetencyDiffPercent() {
        //Check if now is after latestCycle end date the current cycle is the latest cycle
        // else get the previous cycle
        int currentYear = latestCycle.getDueDate().before(Calendar.getInstance().getTime())
                ? latestCycle.getYear()
                : latestCycle.getYear() - 1;
        Integer currentCycleId = competencyCycleRepository.findByYear(currentYear).getId();
        float avgCurrentEvalScore = getAvgEvalScore(currentCycleId);

        //Get previous cycle by current year - 1
        Integer previousYear = currentYear - 1;
        Integer previousCycleId = competencyCycleRepository.findByYear(previousYear).getId();
        float avgPreviousEvalScore = getAvgEvalScore(previousCycleId);

        float diffPercentage = ((avgCurrentEvalScore - avgPreviousEvalScore) / avgPreviousEvalScore) * 100;

        return new CompanyCompetencyDiffPercentDTO(avgCurrentEvalScore, diffPercentage, diffPercentage > 0);
    }

    private float getAvgEvalScore(Integer cycleId) {
        //Get all evaluation overall of all employees have final status is agreed and get the latest cycle
        Specification<CompetencyEvaluationOverall> spec = (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("finalStatus"), "Agreed"),
                criteriaBuilder.equal(root.get("competencyCycle").get("id"), cycleId)
        );
        List<Float> evalScores = evaluationOverallRepository.findAll(spec)
                .stream()
                .map(CompetencyEvaluationOverall::getScore)
                .toList();

        return (float) evalScores.stream().mapToDouble(Float::doubleValue).average().orElse(0);
    }

    @Override
    public List<CompetencyChartDTO> getCompetencyChart() {
        int currentYear = latestCycle.getDueDate().before(Calendar.getInstance().getTime())
                ? latestCycle.getYear()
                : latestCycle.getYear() - 1;
        Integer currentCycleId = competencyCycleRepository.findByYear(currentYear).getId();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CompetencyChartDTO> criteriaQuery = criteriaBuilder.createQuery(CompetencyChartDTO.class);

        Root<CompetencyEvaluation> plRoot = criteriaQuery.from(CompetencyEvaluation.class);
        Join<CompetencyEvaluation, ProficiencyLevel> ceJoin = plRoot.join("proficiencyLevel");
        Join<CompetencyEvaluation, Competency> cJoin = plRoot.join("competency");

        criteriaQuery.multiselect(
                cJoin.get("competencyName"),
                criteriaBuilder.avg(ceJoin.get("score"))
        );

        criteriaQuery.where(
                criteriaBuilder.equal(plRoot.get("competencyCycle").get("id"), currentCycleId)
        );

        criteriaQuery.groupBy(plRoot.get("competency").get("id"));

        TypedQuery<CompetencyChartDTO> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }


    @Override
    public List<EmployeeSkillMatrixDTO> getEmployeeSkillMatrix(Integer empId) {
        Employee employee = employeeManagementService.findEmployee(empId);
        Integer latestCompEvaId = evaluationOverallRepository.latestEvalCompetencyCycle(empId).getId();
        Integer latestCompId = latestCycle.getId();

        List<Competency> competencies = competencyRepository.findAll();

        return competencies.stream()
                .map(competency -> {
                    List<EmployeeSkillMatrixDTO> children =
                            handleChildren(employee, latestCompEvaId, latestCompId, competency.getId());
                    SkillMatrixDataDTO smData = calculateSkillMatrixData(competency.getCompetencyName(), children);
                    return new EmployeeSkillMatrixDTO(smData, children);
                })
                .toList();
    }

    private SkillMatrixDataDTO calculateSkillMatrixData(String competencyName, List<EmployeeSkillMatrixDTO> children) {
        int totalSkillSet = children.size();

        double targetScore = children.stream()
                .map(EmployeeSkillMatrixDTO::getData)
                .filter(Objects::nonNull)
                .mapToDouble(SkillMatrixDataDTO::getTargetSkillLevel)
                .sum();

        double totalScore = children.stream()
                .map(EmployeeSkillMatrixDTO::getData)
                .filter(Objects::nonNull)
                .mapToDouble(SkillMatrixDataDTO::getSkillLevelTotal)
                .sum();

        double selfScore = children.stream()
                .map(EmployeeSkillMatrixDTO::getData)
                .filter(Objects::nonNull)
                .mapToDouble(SkillMatrixDataDTO::getSkillLevelSelf)
                .sum();

        double evaluatorScore = children.stream()
                .map(EmployeeSkillMatrixDTO::getData)
                .filter(Objects::nonNull)
                .mapToDouble(SkillMatrixDataDTO::getSkillLevelManager)
                .sum();

        double competencyScore = children.stream()
                .map(EmployeeSkillMatrixDTO::getData)
                .filter(Objects::nonNull)
                .mapToDouble(SkillMatrixDataDTO::getCompetencyLevel)
                .sum();


        return new SkillMatrixDataDTO(
                competencyName,
                targetScore / totalSkillSet,
                totalScore / totalSkillSet,
                selfScore / totalSkillSet,
                evaluatorScore / totalSkillSet,
                competencyScore / totalSkillSet
        );
    }

    private List<EmployeeSkillMatrixDTO> handleChildren(Employee employee, Integer latestCompEvaId,
                                                        Integer latestCompId, Integer competencyId) {
        List<PositionSkillSet> listPoSs = getPositionSkillSets(employee.getPosition().getId(), competencyId);

        List<Integer> listPoSsIds = listPoSs.stream().map(item -> item.getSkillSet().getId()).toList();
        List<SkillSetEvaluation> ssEvaluates = getSkillSetEvaluations(employee.getId(), latestCompEvaId, listPoSsIds);
        List<SkillSetTarget> ssTargets = getSkillSetTargets(employee.getId(), latestCompId, listPoSsIds);

        return listPoSs.stream()
                .map(item -> {
                    SkillMatrixDataDTO smDataChild = calculateSkillMatrixDataChild(item, ssEvaluates, ssTargets);
                    return new EmployeeSkillMatrixDTO(smDataChild, null);
                })
                .toList();
    }

    private SkillMatrixDataDTO calculateSkillMatrixDataChild(PositionSkillSet item,
                                                             List<SkillSetEvaluation> ssEvaluates,
                                                             List<SkillSetTarget> ssTargets) {
        SkillSetEvaluation ssEva = ssEvaluates.stream()
                .filter(ssEvaluate -> ssEvaluate.getSkillSet().getId() == item.getSkillSet().getId())
                .findFirst()
                .orElse(null);

        SkillSetTarget ssTarget = ssTargets.stream()
                .filter(ssT -> ssT.getSkillSet().getId() == item.getSkillSet().getId())
                .findFirst()
                .orElse(null);

        return ssEva != null && ssTarget != null ? new SkillMatrixDataDTO(
                item.getSkillSet().getSkillSetName(),
                (double) ssTarget.getTargetProficiencyLevel().getScore(),
                (double) ssEva.getFinalProficiencyLevel().getScore(),
                (double) ssEva.getEmployeeProficiencyLevel().getScore(),
                (double) ssEva.getEvaluatorProficiencyLevel().getScore(),
                ((double) ssEva.getFinalProficiencyLevel().getScore() / (double) ssTarget.getTargetProficiencyLevel().getScore()) * 100)
                : null;
    }

    private List<SkillSetTarget> getSkillSetTargets(Integer employeeId, Integer latestCompId,
                                                    List<Integer> listPoSsIds) {
        Specification<SkillSetTarget> ssTSpec = (root, query, builder) -> builder.and(
                builder.equal(root.get("employee").get("id"), employeeId),
                builder.equal(root.get("competencyCycle").get("id"), latestCompId),
                root.get("skillSet").get("id").in(listPoSsIds)
        );
        return skillSetTargetRepository.findAll(ssTSpec);
    }

    private List<PositionSkillSet> getPositionSkillSets(Integer positionId, Integer competencyId) {
        Specification<PositionSkillSet> posSpec = (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("position").get("id"), positionId),
                criteriaBuilder.equal(root.get("skillSet").get("competency").get("id"), competencyId)
        );
        return positionSkillSetRepository.findAll(posSpec);
    }

    private List<SkillSetEvaluation> getSkillSetEvaluations(Integer employeeId, Integer latestCompEvaId,
                                                            List<Integer> listPoSsIds) {
        Specification<SkillSetEvaluation> ssEvaSpec = (root, query, builder) -> builder.and(
                builder.equal(root.get("employee").get("id"), employeeId),
                builder.equal(root.get("competencyCycle").get("id"), latestCompEvaId),
                root.get("skillSet").get("id").in(listPoSsIds)
        );
        return skillSetEvaluationRepository.findAll(ssEvaSpec);
    }


    @Override
    public SkillMatrixOverallDTO getSkillMatrixOverall(Integer empId) {
        CompetencyCycle latestCompEva = evaluationOverallRepository.latestEvalCompetencyCycle(empId);
        Specification<CompetencyEvaluationOverall> spec = (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("employee").get("id"), empId),
                criteriaBuilder.equal(root.get("competencyCycle").get("id"), latestCompEva)
        );

        CompetencyEvaluationOverall eval = evaluationOverallRepository.findOne(spec).orElse(null);
        Employee employee = employeeManagementService.findEmployee(empId);
        return SkillMatrixOverallDTO.builder()
                .managerName(employee.getDepartment().getSum().getFullName())
                .status(Objects.requireNonNull(eval).getFinalStatus()).build();
    }
}

