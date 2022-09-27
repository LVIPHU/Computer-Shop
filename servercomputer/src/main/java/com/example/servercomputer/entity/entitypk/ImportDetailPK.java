package com.example.servercomputer.entity.entitypk;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class ImportDetailPK implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long importId;
	private Long productId;
}
