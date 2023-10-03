package com.hrms.employeemanagement.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.hrms.usermanagement.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends RepresentationModel<Employee> {
	@Id
	@Column(name = "employee_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String lastName;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "gender")
	private String gender;
	@Column(name = "address")
	private String address;
	@Column(name = "email")
	private String email;
	@Column(name = "date_of_birth")
	private String dateOfBirth;
	@Column(name = "phone_number")
	private String phoneNumber;
	@Column(name = "date_joined")
	private String dateJoined;
	@Column(name = "position_level")
	private String positionLevel;

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<EmployeeProject> employeeProjects;

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<EmployeeRole> employeeRoles;

	@OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
	@JsonIgnore
	private User user;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "unit_id")
	@JsonIgnore
	private Unit unit;

	public String getFullname() {
		return firstName + " " + lastName;
	}
}