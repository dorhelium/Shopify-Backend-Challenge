package Doreen.shopifybackendchallenge.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class ImageStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany (orphanRemoval=true, cascade=CascadeType.ALL)
    @JoinColumn(name="imagestore_id")
    List<Image> images;

    public ImageStore() {
        this.images = new ArrayList<>();
    }
}
