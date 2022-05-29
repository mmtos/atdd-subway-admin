package nextstep.subway.domain.factory;

import java.util.Arrays;
import nextstep.subway.domain.Line;
import nextstep.subway.domain.Station;

public class LineFactory {
    public static Line createNewLine(String name, String color, Long distance, Station upStation, Station downStation) {
        return new Line(name,color, distance, Arrays.asList(upStation,downStation));
    }
}
