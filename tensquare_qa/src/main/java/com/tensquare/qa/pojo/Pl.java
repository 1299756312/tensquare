package com.tensquare.qa.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_pl")
@Data
public class Pl implements Serializable {
    private static final long serialVersionUID = -1158141803682305656L;

    @Id
    private String problemId;
    @Id
    private String labelId;
}
