package com.hdtp.dome;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;







@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable{
	
	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;
	private String empno;
	private String name;
	private int age;
}
