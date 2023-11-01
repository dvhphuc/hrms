package com.hrms.careerpathmanagement.services.impl;

import com.hrms.careerpathmanagement.dto.*;
import com.hrms.careerpathmanagement.models.*;
import com.hrms.careerpathmanagement.repositories.*;
import com.hrms.careerpathmanagement.services.CompetencyService;
import com.hrms.employeemanagement.models.*;
import com.hrms.global.paging.Pagination;
import com.hrms.employeemanagement.repositories.*;
import com.hrms.employeemanagement.services.EmployeeManagementService;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import kotlin.Pair;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.hrms.careerpathmanagement.controllers.CompetencyController.setupPaging;

@Service
@Transactional
public class CompetencyServiceImpl implements CompetencyService {
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

    public CompetencyCycle getLastCompetencyCycle(Integer employeeId) {
        Specification<CompetencyEvaluation> spec = hasEmployeeId(employeeId);
        return competencyEvaluationRepository.findOne(spec).orElse(null).getCompetencyCycle();
    }

    public List<SkillSetEvaluation> getSkillEvaluations(Integer employeeId, Integer cycleId) {
        Specification<SkillSetEvaluation> empSpec = hasEmployeeId(employeeId);
        return skillSetEvaluationRepository.findAll(empSpec.and(getCycleSpec(cycleId)));
    }

    @NotNull
    private static <T> Specification<T> getCycleSpec(Integer cycleId) {
        return (root, query, builder) -> builder.equal(root.get("competencyCycle").get("id"), cycleId);
    }

    //TODO: TARGET SKILL SET -> FROM HR SET (BASE ON POSITION & JOB LEVEL)
//    public List<SkillSet> getBaselineSkillsSet(Integer positionId, Integer levelId) {
//        Specification<PositionJobLevelSkillSet> posSpec = hasPosId(positionId);
//        Specification<PositionJobLevelSkillSet> levelSpec = hasLevelId(levelId);
//        return positionJobLevelSkillSetRepository.findAll(posSpec.and(levelSpec))
//                .stream()
//                .map(PositionJobLevelSkillSet::getSkillSet)
//                .toList();  //Have not optimized yet
//    }

    public List<CompetencyEvaluation> getCompetencyEvaluations(Integer employeeId, Integer cycleId) {
        Specification<CompetencyEvaluation> empSpec = hasEmployeeId(employeeId);
        return competencyEvaluationRepository.findAll(empSpec.and(getCycleSpec(cycleId)));
    }

    private <T> Specification<T> hasEmployeeId(Integer employeeId) {
        return (root, query, builder) -> builder.equal(root.get("employee").get("id"), employeeId);
    }

    /**
     * if skillEval is null, currentScore will be null
     * if targetSkill is null, targetScore will be null
     *
     * @return SkillSummarization (DTO)
     */
    public SkillSetSummarization getSkillSummarization(Integer employeeId, Integer cycleId) {
//        //TODO: SQL GROUP BY SKILL SET AND GET AVG OF ALL SKILLS
//        //1. Skill Set Average Score
//        var skillSetAvgScore = getAverageSkillSet(employeeId, cycleId);
//
//        //2. Skill Set Target Score
//        var positionLevel = getPositionLevel(employeeId);
//        var skillSetBaselineScore = getBaselineScore(positionLevel.positionId(), positionLevel.jobLevelId());
//
//        return new SkillSetSummarization(skillSetAvgScore, skillSetBaselineScore);
        return null;
    }

    private Specification<CompetencyEvaluation> empCycleAvgSpec(Integer employeeId, Integer cycleId) {
        return (root, query, builder) -> {
            Join<CompetencyEvaluation, ProficiencyLevel> proficencyJoin = root.join("proficiencyLevel");
            query.groupBy(root.get("employee").get("employeeId"));
            Expression<Double> avgScore = builder.avg(proficencyJoin.get("score"));
            query.multiselect(root.get("employee").get("proficiencyLevel").alias("proficiencyLevel"), avgScore.alias("averageScore"));
            return query.getRestriction();
        };
    }


    private <T> Specification<T> hasCycleId(Integer cycleId) {
        return (root, query, builder) -> builder.equal(root.get("competencyCycle").get("id"), cycleId);
    }

    private <T> Specification<T> hasEmployeeIdAndCycleId(Integer employeeId, Integer cycleId) {
        return (Specification<T>) hasEmployeeId(employeeId).and(hasCycleId(cycleId));
    }

    public Double getAverageSkillSet(Integer empId, Integer cycleId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Double> query = cb.createQuery(Double.class);
        Root<SkillSetEvaluation> root = query.from(SkillSetEvaluation.class);
        Join<SkillSetEvaluation, ProficiencyLevel> proficencyJoin = root.join("employeeProficiencyLevel");

        query.select(cb.avg(proficencyJoin.get("score")));
        query.where(cb.equal(root.get("employee").get("id"), empId), cb.equal(root.get("competencyCycle").get("id"), cycleId));

        return entityManager.createQuery(query).getSingleResult();
    }

//    public Double getBaselineScore(Integer positionId, Integer levelId) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Double> query = cb.createQuery(Double.class);
//        Root<PositionJobLevelSkillSet> root = query.from(PositionJobLevelSkillSet.class);
//        Join<PositionJobLevelSkillSet, ProficiencyLevel> proficencyJoin = root.join("proficiencyLevel");
//
//        query.select(cb.avg(proficencyJoin.get("score")));
//        query.where(cb.equal(root.get("position").get("id"), positionId), cb.equal(root.get("jobLevel").get("id"), levelId));
//
//        return entityManager.createQuery(query).getSingleResult();
//    }

    private PositionLevelDto getPositionLevel(Integer empId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PositionLevelDto> query = cb.createQuery(PositionLevelDto.class);
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
    public List<CompetencyTimeLine> findCompetencyTimeline(Integer competencyCycleId) {
        Specification<CompetencyTimeLine> spec =
                (root, query, cb) -> cb.equal(root.get("competencyCycle").get("id"), competencyCycleId);
        return competencyTimeLineRepository.findAll(spec);
    }

    @Override
    public List<DepartmentInComplete> getDepartmentIncompletePercent(Integer competencyCycleId) {
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

            float employeePercent = getEmployeeInCompletePercent(competencyCycleId, empIdSet);
            float evaluatorPercent = getEvaluatorInCompletePercent(competencyCycleId, empIdSet);

            return new DepartmentInComplete(item, employeePercent, evaluatorPercent);
        }).toList();
    }

    private float getEvaluatorInCompletePercent(Integer competencyCycleId, List<Integer> empIdSet) {
        //get all employees who have completed evaluator-evaluation
        Specification<EvaluationOverall> specCompleteEvaluator = (root, query, criteriaBuilder)
                -> criteriaBuilder.and(
                root.get("employee").get("id").in(empIdSet),
                criteriaBuilder.equal(root.get("evaluatorStatus"), "Completed"),
                criteriaBuilder.equal(root.get("competencyCycle").get("id"), competencyCycleId));

        var evaluatorHasCompleted = evaluationOverallRepository.count(specCompleteEvaluator);
        var evaluatorHasInCompleted = empIdSet.size() - evaluatorHasCompleted;
        return (float) evaluatorHasInCompleted / empIdSet.size() * 100;
    }

    private float getEmployeeInCompletePercent(Integer competencyCycleId, List<Integer> empIdSet) {
        //get all employees who have completed self-evaluation
        Specification<EvaluationOverall> specCompleteEval = (root, query, criteriaBuilder)
                -> criteriaBuilder.and(
                root.get("employee").get("id").in(empIdSet),
                criteriaBuilder.equal(root.get("employeeStatus"), "Completed"),
                criteriaBuilder.equal(root.get("competencyCycle").get("id"), competencyCycleId));

        var employeesHasCompleted = evaluationOverallRepository.count(specCompleteEval);
        var employeesHasInCompleted = empIdSet.size() - employeesHasCompleted;
        return (float) employeesHasInCompleted / empIdSet.size() * 100;
    }

    @Override
    public List<CompanyEvaPercent> getCompanyIncompletePercent(Integer competencyCycleId) {
        List<CompanyEvaPercent> companyEvaPercents = new ArrayList<>();
        List<Integer> empIdSet = employeeRepository
                .findAll()
                .stream()
                .map(Employee::getId)
                .toList();

        //get all employees who have completed evaluation
        Specification<EvaluationOverall> spec = (root, query, criteriaBuilder) -> criteriaBuilder.and(
                root.get("employee").get("id").in(empIdSet),
                criteriaBuilder.equal(root.get("finalStatus"), "Agreed"),
                criteriaBuilder.equal(root.get("competencyCycle").get("id"), competencyCycleId)
        );

        var hasCompletedPercent = (float) evaluationOverallRepository.count(spec) / empIdSet.size() * 100;
        var hasInCompleted = 100 - hasCompletedPercent;

        companyEvaPercents.add(new CompanyEvaPercent("Completed", hasCompletedPercent));
        companyEvaPercents.add(new CompanyEvaPercent("InCompleted", hasInCompleted));
        return companyEvaPercents;
    }

    @Override
    public List<CompetencyEvaluation> findByPositionAndCycle(Integer positionId, Integer competencyCycleId) {
        Specification<CompetencyEvaluation> spec = (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("employee").get("position").get("id"), positionId),
                criteriaBuilder.equal(root.get("competencyCycle").get("id"), competencyCycleId)
        );
        return competencyEvaluationRepository.findAll(spec);
    }

    @Override
    public List<CompetencyEvaluation> findByCycle(Integer competencyCycleId) {
        Specification<CompetencyEvaluation> spec = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("competencyCycle").get("id"), competencyCycleId);
        return competencyEvaluationRepository.findAll(spec);
    }

    @Override
    public List<CompetencyEvaluation> findByCyclesAndDepartment(List<Integer> competencyCyclesId, Integer departmentId) {
        Specification<CompetencyEvaluation> spec = (root, query, criteriaBuilder) -> criteriaBuilder.and(
                root.get("competencyCycle").get("id").in(competencyCyclesId),
                criteriaBuilder.equal(root.get("employee").get("department").get("id"), departmentId)
        );
        return competencyEvaluationRepository.findAll(spec);
    }

    @Override
    public List<AvgCompetency> getAvgCompetencies(Integer positionId, Integer competencyCycleId) {
        List<CompetencyEvaluation> compEvaluates = positionId != null
                ? findByPositionAndCycle(positionId, competencyCycleId)
                : findByCycle(competencyCycleId);
        List<Integer> jobLevelIds = jobLevelRepository
                .findAll()
                .stream()
                .map(JobLevel::getId)
                .toList();
        List<Integer> competencyIds = competencyRepository
                .findAll()
                .stream().
                map(Competency::getId)
                .toList();

        //Join 2 list using flatMap and store it by Pair
        List<Pair<Integer, Integer>> pairItems = jobLevelIds.stream()
                .flatMap(jobLevel -> competencyIds
                        .stream()
                        .map(competency -> new Pair<>(jobLevel, competency)))
                .toList();

        return pairItems.stream().map(pair -> {
            var jobLevel = pair.getFirst();
            var competency = pair.getSecond();
            List<CompetencyEvaluation> evaluationsHasJobLevelAndCompetency = compEvaluates.stream()
                    .filter(compEva -> compEva.getEmployee().getJobLevel().getId() == jobLevel
                            && compEva.getCompetency().getId().equals(competency))
                    .toList();
            float avgScore = evaluationsHasJobLevelAndCompetency.isEmpty() ? 0
                    : (float) evaluationsHasJobLevelAndCompetency.stream()
                    .map(CompetencyEvaluation::getProficiencyLevel)
                    .filter(Objects::nonNull)
                    .mapToInt(ProficiencyLevel::getScore)
                    .average()
                    .orElse(0);
            return new AvgCompetency(jobLevel, competency, avgScore);
        }).toList();
    }

    @Override
    public RadarChart getCompetencyRadarChart(List<Integer> competencyCyclesId, Integer departmentId) {
        var competencyIds = competencyRepository.findAll().stream().map(Competency::getId).toList();
        var competencyEvaluates = findByCyclesAndDepartment(competencyCyclesId, departmentId);
        proficiencyLevelRepository.findAll();

        //Join 2 list using flatMap and store it by Pair
        List<Pair<Integer, Integer>> pairItems = competencyCyclesId.stream()
                .flatMap(cycle -> competencyIds
                        .stream()
                        .map(competency -> new Pair<>(cycle, competency)))
                .toList();

        //Get label for each dataset and RadarDataset have sture list of score RadarDataset(competencyCycleName, listScore)
        List<RadarValue> avgCompetencies = pairItems.stream().map(pair -> {
            var cycle = pair.getFirst();
            var competency = pair.getSecond();
            List<CompetencyEvaluation> compEvaluate = competencyEvaluates.stream()
                    .filter(compEva -> compEva.getCompetencyCycle().getId() == cycle
                            && compEva.getCompetency().getId().equals(competency))
                    .toList();
            float avgScore = compEvaluate.isEmpty() ? 0
                    : (float) compEvaluate.stream()
                    .map(CompetencyEvaluation::getProficiencyLevel)
                    .filter(Objects::nonNull)
                    .mapToInt(ProficiencyLevel::getScore)
                    .average()
                    .orElse(0);
            return new RadarValue(cycle, competency, avgScore);
        }).toList();

        List<RadarDataset> listDataset = competencyCyclesId.stream().map(cycle -> {
            List<Float> listScore = competencyIds
                    .stream()
                    .map(competency -> avgCompetencies.stream().filter(avgCompetency ->
                                    avgCompetency.getCompetencyId().equals(competency) &&
                                            avgCompetency.getCompetencyCycleId().equals(cycle))
                            .findFirst()
                            .map(RadarValue::getAverage).orElse(null)).toList();
            return new RadarDataset(competencyCycleRepository
                    .findAll()
                    .stream()
                    .filter(competencyCycle -> competencyCycle.getId() == cycle)
                    .findFirst()
                    .orElseThrow()
                    .getCompetencyCycleName(), listScore);
        }).toList();

        //Get list labels using spec and return RadarChart
        Specification<Competency> specComp = (root, query, criteriaBuilder) -> root.get("id").in(competencyIds);
        List<String> labels = competencyRepository
                .findAll(specComp)
                .stream()
                .map(Competency::getCompetencyName)
                .toList();
        return new RadarChart(labels, listDataset);
    }

    @Override
    public TopSkillSetPaging getTopHighestSkillSet(@Nullable Integer employeeId,
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

        Page<TopHighestSkillSet> ssEvaluates = skillSetEvaluationRepository
                .findAll(spec, pageable)
                .map(item -> new TopHighestSkillSet(item.getEmployee(),
                        item.getSkillSet(),
                        item.getFinalProficiencyLevel()));
        Pagination pagination = setupPaging(ssEvaluates, pageNo, pageSize);
        return new TopSkillSetPaging(ssEvaluates.getContent(), pagination);
    }

    @Override
    public List<EmployeeSkillMatrix> getEmployeeSkillMatrix(Integer empId) {
        Employee employee = employeeManagementService.findEmployee(empId);
        List<Competency> competencies = competencyRepository.findAll();
        Integer latestCompEvaId = evaluationOverallRepository.latestEvalCompetencyCycle(empId).getId();
        Integer latestCompId = latestCycle.getId();

        return competencies
                .stream()
                .map(item -> {
                    List<EmployeeSkillMatrix> children = handleChildren(employee, latestCompEvaId, latestCompId, item);
                    int totalSkillSet = children.size();
                    float selfScore = 0, evaluatorScore = 0, totalScore = 0, targetScore = 0, competencyScore = 0;
                    for (EmployeeSkillMatrix ssMatrix : children) {
                        selfScore += ssMatrix.data().skillLevelSelf();
                        evaluatorScore += ssMatrix.data().skillLevelManager();
                        totalScore += ssMatrix.data().skillLevelTotal();
                        targetScore += ssMatrix.data().targetSkillLevel();
                        competencyScore += ssMatrix.data().competencyLevel();
                    }
                    SkillMatrixData smData = new SkillMatrixData(item.getCompetencyName(), targetScore / totalSkillSet, totalScore / totalSkillSet,
                            selfScore / totalSkillSet, evaluatorScore / totalSkillSet, competencyScore / totalSkillSet);
                    return new EmployeeSkillMatrix(smData, children);
                })
                .toList();
    }

    private List<EmployeeSkillMatrix> handleChildren(Employee employee, Integer latestCompEvaId,
                                                     Integer latestCompId, Competency competency) {
        Specification<PositionSkillSet> posSpec = (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("position").get("id"), employee.getPosition().getId()),
                criteriaBuilder.equal(root.get("skillSet").get("competency").get("id"), competency.getId())
        );
        List<PositionSkillSet> listPoSs = positionSkillSetRepository.findAll(posSpec);

        List<Integer> skillSetIds = listPoSs.stream().map(poSs -> poSs.getSkillSet().getId()).toList();
        Specification<SkillSetEvaluation> ssEvaSpec = (root, query, builder) -> builder.and(
                builder.equal(root.get("employee").get("id"), employee.getId()),
                builder.equal(root.get("competencyCycle").get("id"), latestCompEvaId),
                root.get("skillSet").get("id").in(skillSetIds)
        );
        List<SkillSetEvaluation> ssEvaluates = skillSetEvaluationRepository.findAll(ssEvaSpec);

        Specification<SkillSetTarget> ssTSpec = (root, query, builder) -> builder.and(
                builder.equal(root.get("employee").get("id"), employee.getId()),
                builder.equal(root.get("competencyCycle").get("id"), latestCompId),
                root.get("skillSet").get("id").in(skillSetIds)
        );
        List<SkillSetTarget> ssTargets = skillSetTargetRepository.findAll(ssTSpec);

        return listPoSs
                .stream()
                .map(item -> {
                    SkillSetEvaluation ssEva = ssEvaluates.stream()
                            .filter(ssEvaluate -> ssEvaluate.getSkillSet().getId() == item.getSkillSet().getId())
                            .findFirst()
                            .orElse(null);
                    SkillSetTarget ssTarget = ssTargets.stream()
                            .filter(ssT -> ssT.getSkillSet().getId() == item.getSkillSet().getId())
                            .findFirst()
                            .orElse(null);
                    SkillMatrixData smDataChild = ssEva != null && ssTarget != null ?
                            new SkillMatrixData(item.getSkillSet().getSkillSetName(), (float) ssTarget.getTargetProficiencyLevel().getScore(),
                                    (float) ssEva.getFinalProficiencyLevel().getScore(), (float) ssEva.getEmployeeProficiencyLevel().getScore(),
                                    (float) ssEva.getEvaluatorProficiencyLevel().getScore(),
                                    ((float) ssEva.getFinalProficiencyLevel().getScore() / (float) ssTarget.getTargetProficiencyLevel().getScore()) * 100)
                            : null;
                    return new EmployeeSkillMatrix(smDataChild, null);
                })
                .toList();
    }

    @Override
    public SkillMatrixOverall getSkillMatrixOverall(Integer empId) {
        CompetencyCycle latestCompEva = evaluationOverallRepository.latestEvalCompetencyCycle(empId);
        Specification<EvaluationOverall> spec = (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("employee").get("id"), empId),
                criteriaBuilder.equal(root.get("competencyCycle").get("id"), latestCompEva)
        );

        EvaluationOverall eval = evaluationOverallRepository.findOne(spec).orElse(null);
        Employee employee = employeeManagementService.findEmployee(empId);
        return SkillMatrixOverall.builder()
                .managerName(employee.getDepartment().getSum().getFullName())
                .status(Objects.requireNonNull(eval).getFinalStatus()).build();
    }

    @Override
    public TopSkillSetPaging getTopKeenSkillSetEmployee(Integer employeeId, int pageNo, int pageSize) {
        CompetencyCycle evalLatestCycle = evaluationOverallRepository.latestEvalCompetencyCycle(employeeId);
        skillSetRepository.findAll();
        proficiencyLevelRepository.findAll();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Specification<SkillSetEvaluation> spec = (root, query, builder) -> {
            query.orderBy(builder.asc(root.get("finalProficiencyLevel")));
            query.where(builder.equal(root.get("competencyCycle").get("id"), evalLatestCycle.getId()),
                    builder.equal(root.get("employee").get("id"), employeeId));
            return query.getRestriction();
        };

        Page<TopHighestSkillSet> topsHighest = skillSetEvaluationRepository
                .findAll(spec, pageable)
                .map(item -> new TopHighestSkillSet(item.getEmployee(),
                        item.getSkillSet(),
                        item.getFinalProficiencyLevel()));
        Pagination pagination = setupPaging(topsHighest, pageNo, pageSize);
        return new TopSkillSetPaging(topsHighest.getContent(), pagination);
    }

    @Override
    public TopSkillSetPaging getTopHighestSkillSetTargetEmployee(Integer employeeId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        skillSetRepository.findAll();

        Specification<SkillSetTarget> spec = (root, query, builder) -> {
            query.orderBy(builder.desc(root.get("targetProficiencyLevel")));
            query.where(builder.equal(root.get("competencyCycle").get("id"), latestCycle.getId()),
                    builder.equal(root.get("employee").get("id"), employeeId));
            return query.getRestriction();
        };

        Page<TopHighestSkillSet> topsHighest = skillSetTargetRepository
                .findAll(spec, pageable)
                .map(item -> new TopHighestSkillSet(item.getEmployee(),
                        item.getSkillSet(),
                        item.getTargetProficiencyLevel()));
        Pagination pagination = setupPaging(topsHighest, pageNo, pageSize);
        return new TopSkillSetPaging(topsHighest.getContent(), pagination);
    }

    @Override
    public CurrentEvaluation getCurrentEvaluation(Integer employeeId) {
        Specification<EvaluationOverall> spec = (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("employee").get("id"), employeeId),
                criteriaBuilder.equal(root.get("competencyCycle").get("id"), latestCycle.getId())
        );
        EvaluationOverall evalOvr = evaluationOverallRepository.findOne(spec).orElse(null);
        if (evalOvr == null) return new CurrentEvaluation(latestCycle.getCompetencyCycleName(), "Not Started", null);
        return new CurrentEvaluation(evalOvr.getCompetencyCycle().getCompetencyCycleName(),
                evalOvr.getFinalStatus(), evalOvr.getLastUpdated().toString());
    }

    @Override
    public List<HistoryEvaluation> getHistoryEvaluations(Integer employeeId) {
        List<CompetencyCycle> compCycles = competencyCycleRepository.findAll();
        List<Integer> cycleIds = compCycles.stream().map(CompetencyCycle::getId).toList();
        Specification<EvaluationOverall> spec = (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("employee").get("id"), employeeId),
                root.get("competencyCycle").get("id").in(cycleIds)
        );

        return evaluationOverallRepository
                .findAll(spec)
                .stream()
                .map(evalOvr -> new HistoryEvaluation(evalOvr.getCompletedDate().toString(),
                        evalOvr.getCompetencyCycle().getCompetencyCycleName(), evalOvr.getFinalStatus(), evalOvr.getScore()))
                .toList();
    }


    @Override
    public CompanyCompetencyDiffPercent getCompanyCompetencyDiffPercent() {
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

        return new CompanyCompetencyDiffPercent(avgCurrentEvalScore, diffPercentage, diffPercentage > 0);
    }

    private float getAvgEvalScore(Integer cycleId) {
        //Get all evaluation overall of all employees have final status is agreed and get the latest cycle
        Specification<EvaluationOverall> spec = (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("finalStatus"), "Agreed"),
                criteriaBuilder.equal(root.get("competencyCycle").get("id"), cycleId)
        );
        List<Float> evalScores = evaluationOverallRepository.findAll(spec)
                .stream()
                .map(EvaluationOverall::getScore)
                .toList();

        return (float) evalScores.stream().mapToDouble(Float::doubleValue).average().orElse(0);
    }

    @Override
    public List<CompetencyChart> getCompetencyChart() {
        //Get all competencyEvaluation have competencyCycle = competencyCycle and proficiencyLevel != null
        Specification<CompetencyEvaluation> spec = (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("competencyCycle").get("id"), latestCycle.getId()),
                criteriaBuilder.isNotNull(root.get("proficiencyLevel"))
        );
        List<CompetencyEvaluation> competencyEvaluations = competencyEvaluationRepository.findAll(spec);

        List<Competency> competencies = competencyRepository.findAll();
        return competencies.stream().map(item -> {
            List<CompetencyEvaluation> competencyEvaluates = competencyEvaluations.stream()
                    .filter(competencyEvaluate -> competencyEvaluate.getCompetency().getId().equals(item.getId()))
                    .toList();
            float avgScore = competencyEvaluates.isEmpty() ? 0
                    : (float) competencyEvaluates.stream()
                    .map(CompetencyEvaluation::getProficiencyLevel)
                    .filter(Objects::nonNull)
                    .mapToInt(ProficiencyLevel::getScore)
                    .average()
                    .orElse(0);
            return new CompetencyChart(item.getCompetencyName(), avgScore);
        }).toList();
    }
}

