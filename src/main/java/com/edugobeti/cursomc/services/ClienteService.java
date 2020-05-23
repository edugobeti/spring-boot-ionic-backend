package com.edugobeti.cursomc.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edugobeti.cursomc.domain.Cidade;
import com.edugobeti.cursomc.domain.Cliente;
import com.edugobeti.cursomc.domain.Endereco;
import com.edugobeti.cursomc.domain.enuns.TipoCliente;
import com.edugobeti.cursomc.dto.ClienteDTO;
import com.edugobeti.cursomc.dto.ClienteNewDTO;
import com.edugobeti.cursomc.repository.ClienteRepository;
import com.edugobeti.cursomc.repository.EnderecoRepository;
import com.edugobeti.cursomc.service.exception.DataIntegratyException;

@Service
public class ClienteService {
	
	@Autowired 
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id){
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new com.edugobeti.cursomc.service.exception.ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return obj = repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegratyException("Não é possível deletar um cliente com pedidos associados!");
		}
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		
		return obj;
	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	public Page<Cliente> findPages(Integer page, Integer linesPerPage, String direction, String orderBy){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy );
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()));	
		Cidade ci = new Cidade(objDTO.getCidade(), null, null);
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, ci);
		
		cli.setEnderecos(Arrays.asList(end));
		cli.getTelefones().addAll(Arrays.asList(objDTO.getTelefone1()));
		
		if(objDTO.getTelefone2() != null) {
			cli.getTelefones().addAll(Arrays.asList(objDTO.getTelefone2()));
		}
		if(objDTO.getTelefone3() != null) {
			cli.getTelefones().addAll(Arrays.asList(objDTO.getTelefone3()));
		}
		
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		
	}
}
