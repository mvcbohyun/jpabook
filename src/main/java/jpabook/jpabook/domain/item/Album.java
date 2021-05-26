package jpabook.jpabook.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import jpabook.jpabook.domain.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("A")
@Getter @Setter
public class Album extends Item {
 private String artist;
 private String etc;
}