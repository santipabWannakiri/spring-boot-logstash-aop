package com.logstash.aop.serviceImp;


import com.logstash.aop.model.Instructor;
import com.logstash.aop.repository.InstructorRepository;
import com.logstash.aop.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorImpl implements InstructorService {

    private InstructorRepository instructorRepository;

    @Autowired
    public InstructorImpl(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Override
    public void save(Instructor instructor) {
        instructorRepository.save(instructor);
    }

    @Override
    public Optional<Instructor> findById(int id) {
    return instructorRepository.findById(id);
    }

    @Override
    public List<Instructor> findAll() {
        return (List<Instructor>) instructorRepository.findAll();
    }

    @Override
    public void deleteById(int id) {
        instructorRepository.deleteById(id);
    }
}
