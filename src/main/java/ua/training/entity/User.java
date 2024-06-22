package ua.training.entity;

import java.math.BigDecimal;
import java.sql.Date;

public class User {

	private Long id;
	private String name;
	private String surname;

	private String pathronymic;
	private BigDecimal salary;
	private Date birth;
	private Date start;
	private String phone;
	private Role role;
	private String city;
	private String street;
	private String zip;
	private String email;
	private String password;

	public User() {

	}

	public static class Builder implements IBuilder<User> {

		private User user = new User();

		public Builder setId(Long id) {
			user.id = id;
			return this;
		}

		public Builder setName(String name) {
			user.name = name;
			return this;
		}

		public Builder setSurname(String surname) {
			user.surname = surname;
			return this;
		}

		public Builder setPathronymic(String pathronymic) {
			user.pathronymic = pathronymic;
			return this;
		}

		public Builder setSalary(BigDecimal salary) {
			user.salary = salary;
			return this;
		}

		public Builder setBirth(Date birth) {
			user.birth = birth;
			return this;
		}

		public Builder setStart(Date start) {
			user.start = start;
			return this;
		}

		public Builder setPhone(String phone) {
			user.phone = phone;
			return this;
		}

		public Builder setRole(Role role) {
			user.role = role;
			return this;
		}

		public Builder setCity(String city) {
			user.city = city;
			return this;
		}

		public Builder setStreet(String street) {
			user.street = street;
			return this;
		}

		public Builder setZip(String zip) {
			user.zip = zip;
			return this;
		}

		public Builder setEmail(String email) {
			user.email = email;
			return this;
		}

		public Builder setPassword(String password) {
			user.password = password;
			return this;
		}

		@Override
		public User build() {
			return user;
		}

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPathronymic() {
		return pathronymic;
	}

	public void setPathronymic(String pathronymic) {
		this.pathronymic = pathronymic;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		
		User other = (User) obj;
		
		if ((email != null) ? !email.equals(other.email) : other.email != null ) {
			return false;
		}
		
		if ((password != null) ? !password.equals(other.password) : other.password != null ) {
			return false;
		}

		return ((phone != null) ? phone.equals(other.phone) : other.phone == null);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=").append(id).append(", name=").append(name).append(", surname=").append(surname)
				.append(", pathronymic=").append(pathronymic).append(", salary=").append(salary).append(", birth=")
				.append(birth).append(", start=").append(start).append(", phone=").append(phone).append(", role=")
				.append(role).append(", city=").append(city).append(", street=").append(street).append(", zip=")
				.append(", email=").append(email).append(", password=").append(password).append("]");
		return builder.toString();
	}

}
