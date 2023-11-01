package com.hrms.employeemanagement.models;

import com.hrms.usermanagement.model.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	@Id
	@Column(name = "employee_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "email")
	private String email;
	@Column(name = "joined_date")
	private Date joinedDate;
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private Department department;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "position_id")
	private Position position;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "job_level_id")
	private JobLevel jobLevel;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	@Column(name = "status")
	private Integer status;
	@Column(name = "left_date")
	private Date leftDate;
	@Nullable
	@Column(name = "dam_id")
	private Integer damId;
	public String getFullName() {
		return this.lastName + " " + this.firstName;
	}
}