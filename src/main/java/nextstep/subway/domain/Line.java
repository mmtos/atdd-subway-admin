package nextstep.subway.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Line extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String color;

    @Embedded
    private Sections sections = new Sections();

    protected Line() {
    }

    public Line(String name, String color, Section initSection, Section lastSection) {
        this.name = name;
        this.color = color;
        this.sections.add(initSection);
        this.sections.add(lastSection);
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void updateLine(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Sections getSections() {
        return sections;
    }
}
