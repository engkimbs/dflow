package com.lucorda.dflow.data.api;

import com.lucorda.dflow.data.api.dto.DataApiRequest;
import com.lucorda.dflow.data.query.entity.CompanyInfo;
import com.lucorda.dflow.data.query.entity.District;
import com.lucorda.dflow.data.query.entity.Sector;
import com.lucorda.dflow.data.query.service.DataService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/data")
public class DataApiController {

    DataService dataService;

    public DataApiController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/categories")
    public ResponseEntity<Result<List<Sector>>> getAllSectors() {
        List<Sector> sectors = dataService.findAllSectors();
        return ResponseEntity.ok().body(new Result<>(sectors, sectors.size()));
    }

    @GetMapping("/districts")
    public ResponseEntity<Result<List<District>>> getAllDistricts() {
        List<District> districts = dataService.findAllDistricts();
        return ResponseEntity.ok().body(new Result<>(districts, districts.size()));
    }

    @GetMapping("/company")
    public ResponseEntity<Page<CompanyInfo>> getCompanyInfosWithRequestCondition(
            @RequestBody DataApiRequest dataApiRequest,
            @PageableDefault(size=50, sort="registerDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CompanyInfo> companyInfoPage = dataService.findCompanyInfoWithRequestCondition(dataApiRequest, pageable);
        return ResponseEntity.ok().body(companyInfoPage);
    }

    @Getter
    @Setter
    static class Result<T> {
        private T data;
        private int count;

        public Result(T data, int count) {
            this.data = data;
            this.count = count;
        }
    }
}
