package com.wpa.mm.student_registration.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "academicYear is required")
	private Integer academicYear;
	
	@Min(value = 0,message = "must not be negative")
	private Integer myanmar;
	
	@Min(value = 0,message = "must not be negative")
	private Integer english;
	
	@Min(value = 0,message = "must not be negative")
	private Integer mathematic;
	
	@Min(value = 0,message = "must not be negative")
	private Integer history;
	
	@Min(value = 0,message = "must not be negative")
	private Integer science;
	
	@Min(value = 0,message = "total must not be negative")
	private Integer total;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "student_id")
	@JsonIgnore
	private Student student;

	@Override
	public String toString() {
		return "Report [id=" + id + ", academicYear=" + academicYear + ", myanmar=" + myanmar + ", english=" + english
				+ ", mathematic=" + mathematic + ", history=" + history + ", science=" + science + ", total=" + total
				+ ", student=" + student + "]";
	}

	
}
