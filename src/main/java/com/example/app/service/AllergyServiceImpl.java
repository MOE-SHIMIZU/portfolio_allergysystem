package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.Allergy;
import com.example.app.mapper.AllergyMapper;

@Service
@Transactional(rollbackFor = Exception.class)
public class AllergyServiceImpl implements AllergyService {

	@Autowired
	private AllergyMapper allergyMapper;

	@Override
	public List<Allergy> getAllergyList() throws Exception {
		return allergyMapper.selectAll();
	}

	@Override
	public Allergy getAllergyById(Integer id) throws Exception {
		return allergyMapper.selectById(id);
	}

	@Override
	public void deleteAllergyById(Integer id) throws Exception {
		allergyMapper.deleteById(id);

	}


	@Override
	public void editAllergy(Allergy allergy) throws Exception {
		allergyMapper.update(allergy);
	}

	// 2. 一括挿入用
	@Override
	public void addAllergyList(List<Allergy> allergies) throws Exception {
		allergyMapper.insertList(allergies);
	}

	//	ページネーション
	@Override
	public int getTotalPages(int numPerPage) throws Exception {
		double totalNum = (double) allergyMapper.count();
		return (int) Math.ceil(totalNum / numPerPage);
	}

	//	ページネーション
	@Override
	public List<Allergy> getAllergyListByPage(int page, int numPerPage) throws Exception {
		int offset = numPerPage * (page - 1);
		return allergyMapper.selectLimited(offset, numPerPage);
	}


}
