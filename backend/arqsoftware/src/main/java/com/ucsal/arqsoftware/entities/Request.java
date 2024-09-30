package com.ucsal.arqsoftware.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_requests")
@Getter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Setter
	private Long id;
	
	@Setter
	private Date dateTimeStart;
	
	@Setter
	private Date dateTimeEnd;
	
	@Setter
	private Date dateCreationRequest;
	
	@Setter
	private String needs;
	
	@Setter
	private RequestStatus status;
	
	@ManyToOne
	@JoinColumn(name = "space_id")
	@Setter
	private PhysicalSpace physicalSpace;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@Setter
	private User user;
	
	@ManyToMany(mappedBy = "requests")
	@Setter
	private Set<ApprovalHistory> approvalHistories = new HashSet<>();
	
	public Request() {
	}
	
}
