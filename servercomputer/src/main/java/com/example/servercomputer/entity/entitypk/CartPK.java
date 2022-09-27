package com.example.servercomputer.entity.entitypk;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CartPK implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long productId;
	private Long userId;
}
