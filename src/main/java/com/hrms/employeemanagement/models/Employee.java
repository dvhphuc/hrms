package com.hrms.employeemanagement.models;

import com.hrms.employeecompetency.models.PositionLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends RepresentationModel<Employee> {
	@Id
	@Column(name = "employee_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "gender")
	private String gender;
	@Column(name = "address")
	private String address;
	@Column(name = "date_of_birth")
	private Date dateOfBirth;
	@Column(name = "phone_number")
	private String phoneNumber;
	@Column(name = "current_contract")
	private Integer currentContract;
	@Column(name = "profile_bio")
	private String profileBio;
	@Column(name = "facebook_link")
	private String facebookLink;
	@Column(name = "twitter_link")
	private String twitterLink;
	@Column(name = "linkedin_link")
	private String linkedinLink;
	@Column(name = "instagram_link")
	private String instagramLink;
	@Lob
	@Column(name = "profile_picture")
	private String profilePicture;
	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;
	@ManyToOne
	@JoinColumn(name = "position_level_id")
	private PositionLevel positionLevel;
	@OneToMany(mappedBy = "employee")
	private List<EmergencyContact> emergencyContacts;
	@OneToMany(mappedBy = "employee")
	private List<EmployeeSkill> employeeSkills;
	@OneToMany(mappedBy = "employee")
	private List<EmployeeProject> employeeProjects;
	@OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
	private User user;
	private String getFullName() {
		return this.firstName + " " + this.lastName;
	}
	private String getDateOfBirth() {
		return this.dateOfBirth.toString();
	}
}