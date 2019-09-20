package com.junjie.bookplatform.Model;

import javax.persistence.*;

@Entity
@Table(name = "major")
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(length = 10,unique = true,updatable = false)
    private String majorName;

    @Column(length = 10,updatable = false)
    private String department;

    public Long getID() {
        return ID;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
