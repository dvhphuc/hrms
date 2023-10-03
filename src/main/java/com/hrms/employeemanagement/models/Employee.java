package com.hrms.employeemanagement.models;

import com.hrms.usermanagement.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Optional;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
public class Employee extends RepresentationModel<Employee> {
	@Id
	@Column(name = "employee_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	@Column(name = "firstname")
	private String firstName;
	@Column(name = "lastname")
	private String lastName;
	@Column(name = "email")
	private String email;
	@Column(name = "gender")
	private String gender;
	@Column(name = "dateofbirth")
	private String dateOfBirth;
	@Column(name = "phonenumber")
	private String phoneNumber;
	@Column(name = "address")
	private String address;
	@Column(name = "positionlevel")
	private String positionLevel;
	@Column(name = "datejoined")
	private String dateJoined;
	@Column(name = "teamunit")
	private String teamUnit;

	public String printOut() {
		return "Employee{" +
				"id='" + id + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				'}';
	}

	@OneToOne(mappedBy = "employee")
	private User user;

	public String getFullname() {
		return firstName + " " + lastName;
	}
}