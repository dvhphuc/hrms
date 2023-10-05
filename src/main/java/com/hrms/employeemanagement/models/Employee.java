package com.hrms.employeemanagement.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrms.usermanagement.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

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
	@Column(name = "current_contract")
	private int currentContract;
	@Column(name = "profile_bio")
	private String profileBio;

	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;

	@ManyToOne
	@JoinColumn(name = "position_level_id")
	private PositionLevel positionLevel;

	@OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
	@JsonIgnore
	private User user;

	public String getFullname() {
		return firstName + " " + lastName;
	}
}