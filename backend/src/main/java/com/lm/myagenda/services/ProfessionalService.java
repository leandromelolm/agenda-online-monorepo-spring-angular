package com.lm.myagenda.services;

import com.lm.myagenda.dto.ProfessionalDTO;
import com.lm.myagenda.models.Professional;
import com.lm.myagenda.repositories.AgendaRepository;
import com.lm.myagenda.repositories.AttendanceRepository;
import com.lm.myagenda.repositories.ProfessionalRepository;
import com.lm.myagenda.services.exceptions.DataIntegratyViolationException;
import com.lm.myagenda.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessionalService {

    @Autowired
    ProfessionalRepository repository;

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    AgendaRepository agendaRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<Professional> findAll() {
        return repository.findAll();
    }

    public Professional findById(Long id) {
        Optional<Professional> professionalOptional = repository.findById(id);
        return professionalOptional.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public Page<ProfessionalDTO> findByNameOrCpfOrMatricula(
            String search, Integer page, Integer size, String orderBy, String direction) {
        Pageable pageable =  PageRequest.of(page, size, Sort.Direction.valueOf(direction),orderBy);
        if(search.isBlank()){
            Page<Professional> p = repository.findAllPaged(PageRequest.of(page,size));
            return p.map(x -> new ProfessionalDTO(x));
        }
        if(isNumber(search)){
            Page<Professional> p = repository.findByCpfOrMatricula(search, pageable);
            return p.map(x -> new ProfessionalDTO(x));
        }
        Page<Professional> p = repository.findByNomeContainingIgnoreCase(search, pageable);
        return p.map(x -> new ProfessionalDTO(x));
    }

    public Professional create(ProfessionalDTO obj) {
        existsRegister(obj);
        obj.setStatus("Ativo");
        obj.setDataCadastro(Instant.ofEpochSecond(System.currentTimeMillis()/1000));
        return repository.save(modelMapper.map(obj, Professional.class));
    }

    public Professional udpate(ProfessionalDTO objDto) {
        existsRegister(objDto);
        Professional obj = findById(objDto.getId());
        objDto.setDataCadastro(obj.getDataCadastro());
        objDto.setStatus(obj.getStatus());
        objDto.setDataAlteracaoStatus(Instant.ofEpochSecond(System.currentTimeMillis()/1000));
        return repository.save(modelMapper.map(objDto, Professional.class));
    }

    public void delete(Long id) {
        if(attendanceRepository.existsProfessionalInSomeAttendance(findById(id).getId())){
            throw new DataIntegratyViolationException("Violação de integridade! não é possível deletar!");
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    private void existsRegister(ProfessionalDTO dto){
        Optional<Professional> optCpf = repository.findByCpf(dto.getCpf());
        if(optCpf.isPresent() && !optCpf.get().getId().equals(dto.getId())) {
            throw new DataIntegratyViolationException("CPF já cadastrado no sistema");
        }
        Optional<Professional> optEmail = repository.findByEmail(dto.getEmail());
        if(optEmail.isPresent() && !optEmail.get().getId().equals(dto.getId())) {
            throw new DataIntegratyViolationException("E-mail já cadastrado no sistema");
        }
        Optional<Professional> optMat = repository.findByMatricula(dto.getMatricula());
        if(optMat.isPresent() && !optMat.get().getId().equals(dto.getId())) {
            throw new DataIntegratyViolationException("Matricula já cadastrado no sistema");
        }
    }

    public boolean isNumber(String s){
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
