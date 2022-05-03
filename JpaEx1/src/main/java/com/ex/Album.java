package com.ex;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/*
    DTYPE에 들어갈 값을 변경할 수 있음. 인자를 설정하지 않으면 기본 엔티티명이 적용됨.
 */
@Entity
@DiscriminatorValue("A")
public class Album extends Item {
    private String artist;
}
