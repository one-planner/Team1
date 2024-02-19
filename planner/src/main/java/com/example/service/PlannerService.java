package com.example.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Plan;
import com.example.repository.PlannerRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlannerService {

	@Autowired
	public PlannerRepository plannerRepository;

	@Transactional
	public Plan insert(Plan plan) {
		System.out.println("이벤트 insert 요청 서비스에 들어왔습니다");
		try {
			// 일정 추가
			Plan newPlan = plannerRepository.save(plan);
			System.out.println("새로운 일정" + newPlan);
			return plan;
		} catch (Exception e) {
			// 예외 로깅
			System.out.println("에러 발생: " + e.getMessage());
			// 필요한 경우 클라이언트에게 에러 응답 반환
			// 여기서는 null을 반환하거나, 사용자 정의 예외를 던질 수 있습니다.
			return null;
		}
	}


	public List<Map<String, Object>> selectList(User user) {
		List<Plan> planList = plannerRepository.findAllByUser(user);

		// Plan 객체를 Map으로 변환하는 로직을 추가하면 됩니다.
        List<Map<String, Object>> eventList =
        	planList.stream().map(plan -> {

	        	Map<String, Object> event = new HashMap<>();
			    event.put("id",plan.getPlanId());
			    event.put("title", plan.getTitle());
			    event.put("allDay", plan.isAllDay());
			    event.put("startDate", plan.getStartDate());
			    event.put("startTime", plan.getStartTime());
			    event.put("endDate", plan.getEndDate());
			    event.put("endTime", plan.getEndTime());
			    event.put("place", plan.getPlace());
			    event.put("repeat", plan.getRepeat());
			    event.put("content", plan.getContent());
			    event.put("alarm", plan.isAlarm());

    		    return event;
    		}).collect(Collectors.toList());
        	return eventList;
	}

	@Transactional
	public Optional<Plan> selectDetail(Long id) { // 일정 상세 조회
		System.out.println(id + " " + plannerRepository.findById(id));
		return plannerRepository.findById(id);
	}

	@Transactional
	public Plan update(Plan plan) { // 일정 수정
		System.out.println("DB에 담을 새일정");
	    Plan rePlan = plannerRepository.findById(plan.getPlanId()).get();
	    rePlan.setTitle(plan.getTitle());
	    rePlan.setAllDay(plan.isAllDay());
	    rePlan.setStartDate(plan.getStartDate());
	    rePlan.setStartTime(plan.getStartTime());
	    rePlan.setEndDate(plan.getEndDate());
	    rePlan.setEndTime(plan.getEndTime());
	    rePlan.setRepeat(plan.getRepeat());
	    rePlan.setPlace(plan.getPlace());
	    rePlan.setContent(plan.getContent());
	    rePlan.setAlarm(plan.isAlarm());
		System.out.println(rePlan);
	    return plannerRepository.save(rePlan);
	}

    public void delete(Long	Id) { // 일정 삭제
		plannerRepository.deleteById(Id);
    }


	public Map<String, Object> selectPlan(Long planId, User user) {
		Plan plan = plannerRepository.findByplanIdAndUser(planId, user).get();
		Map<String, Object> event = new HashMap<>();
		// Plan 객체를 Map으로 변환하는 로직
		event.put("id",plan.getPlanId());
		event.put("title", plan.getTitle());
		event.put("allDay", plan.isAllDay());
		event.put("startDate", plan.getStartDate());
		event.put("startTime", plan.getStartTime());
		event.put("endDate", plan.getEndDate());
		event.put("endTime", plan.getEndTime());
		event.put("place", plan.getPlace());
		event.put("repeat", plan.getRepeat());
		event.put("content", plan.getContent());
		event.put("alarm", plan.isAlarm());
		return event;
	}
}