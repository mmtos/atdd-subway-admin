package nextstep.subway.application;

import java.util.Optional;
import nextstep.subway.domain.Line;
import nextstep.subway.domain.LineRepository;
import nextstep.subway.domain.Sections;
import nextstep.subway.domain.Station;
import nextstep.subway.domain.StationRepository;
import nextstep.subway.domain.exception.CannotDeleteSectionException;
import nextstep.subway.domain.factory.SectionFactory;
import nextstep.subway.dto.SectionRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SectionService {
    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

    public SectionService(LineRepository lineRepository, StationRepository stationRepository) {
        this.lineRepository = lineRepository;
        this.stationRepository = stationRepository;
    }

    public Line createSection(Long lineId, SectionRequest sectionRequest) {
        Line line = lineRepository.getById(lineId);
        Station upStation = stationRepository.findById(sectionRequest.getUpStationId()).get();
        Station downStation = stationRepository.findById(sectionRequest.getDownStationId()).get();
        Sections sections = line.getSections();
        sections.insertSection(
                SectionFactory.createSectionAtMiddleOfLine(upStation, downStation, sectionRequest.getDistance()));
        return line;
    }

    public void deleteSection(Long lineId, Long stationId) {
        Line line = lineRepository.getById(lineId);
        if(line.hasOnlyOneActualSection()){
            throw new CannotDeleteSectionException("노선은 최소 1개의 구간을 가져야 합니다.");
        }
        Station deleteStation = stationRepository.findById(stationId).get();
        Sections sections = line.getSections();
        sections.deleteSection(deleteStation);
    }
}
