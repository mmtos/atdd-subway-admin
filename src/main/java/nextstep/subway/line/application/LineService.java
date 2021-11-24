package nextstep.subway.line.application;

import nextstep.subway.common.ErrorCode;
import nextstep.subway.exception.NotFoundApiException;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineRepository;
import nextstep.subway.line.dto.LineRequest;
import nextstep.subway.line.dto.LineResponse;
import nextstep.subway.section.service.SectionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LineService {

    private final LineRepository lineRepository;
    private final SectionService sectionService;

    public LineService(LineRepository lineRepository, SectionService sectionService) {
        this.lineRepository = lineRepository;
        this.sectionService = sectionService;
    }

    public Line saveLine(LineRequest request) {
        Line line = lineRepository.save(request.toLine());
        sectionService.saveSection(request.getUpStationId(), request.getDownStationId(), line, request.getDistance());
        return line;
    }

    public List<LineResponse> findAllLines() {
        List<Line> lines = lineRepository.findAll();

        List<LineResponse> responses = new ArrayList<>();
        for (Line line : lines) {
            responses.add(LineResponse.of(line));
        }
        return responses;
    }

    public LineResponse findLine(Long lineId) {
        Line line = findById(lineId);
        return LineResponse.of(line);
    }

    public LineResponse updateLine(Long lineId, LineRequest lineRequest) {
        Line line = findById(lineId);
        line.update(lineRequest.toLine());
        return LineResponse.of(line);
    }

    public void deleteLine(Long lineId) {
        Line line = findById(lineId);
        lineRepository.delete(line);
    }

    private Line findById(Long lineId) {
        return lineRepository.findById(lineId)
                .orElseThrow(() -> new NotFoundApiException(ErrorCode.NOT_FOUND_LINE_ID));
    }
}
