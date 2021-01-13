package Doreen.shopifybackendchallenge.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class Image implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private byte[] imageData;
    private boolean isPublic;

    public Image(byte[] imageData) {
        this.imageData = imageData;
    }

    public Image() {

    }
}
