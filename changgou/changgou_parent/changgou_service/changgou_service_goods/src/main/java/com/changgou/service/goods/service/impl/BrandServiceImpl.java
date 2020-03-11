package com.changgou.service.goods.service.impl;

import com.changgou.common.pojo.Page;
import com.changgou.goods.pojo.Brand;
import com.changgou.service.goods.dao.BrandMapper;
import com.changgou.service.goods.service.BrandService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
   private BrandMapper brandMapper;


    /*
    * 品牌列表查询
    * */
    public List<Brand> findList() {
        List<Brand> brandList = brandMapper.selectAll();
        return brandList;
    }

    /*
     * 根据Id查询品牌数据
     * */
    @Override
    public Brand findById(Integer id) {
       Brand brand= brandMapper.selectByPrimaryKey(id);
        return brand;
    }

    /*
     * 品牌新增
     * */
    @Override
    @Transactional
    public void add(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    /*
     * 品牌修改
     * */
    @Override
    @Transactional
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKey(brand);
    }

    /*
     * 品牌删除
     * */
    @Override
    public void delById(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    /*
     * 品牌列表条件查询
     * */
    @Override
    public List<Brand> list(Map<String, Object> searchMap) {
        Example example = new Example(Brand.class);
        //封装查询条件
        Example.Criteria criteria = example.createCriteria();
        if (searchMap!=null){
            //品牌名称(模糊) like  %
            if (searchMap.get("name")!=null && !"".equals(searchMap.get("name"))){
                criteria.andLike("name","%"+searchMap.get("name")+"%");
            }
            //按照品牌首字母进行查询(精确)
            if (searchMap.get("letter")!=null && !"".equals(searchMap.get("letter"))){
                criteria.andEqualTo("letter",searchMap.get("letter"));
            }
        }
        List<Brand> brandList = brandMapper.selectByExample(example);
        return brandList;
    }
    @Override
    public Page<Brand> findPage(int page, int size) {
        PageHelper.startPage(page,size);
        Page<Brand> page1 = (Page<Brand>) brandMapper.selectAll();
        return page1;
    }

    /*
     * 品牌列表分页+条件查询
     * */
    @Override
    public Page<Brand> findPage(Map<String, Object> searchMap, int page, int size) {
        //设置分页
        PageHelper.startPage(page,size);

        //设置查询条件
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap!=null){
            //设置品牌名称模糊查询
            if (searchMap.get("name")!=null && !"".equals(searchMap.get("name"))){
                criteria.andLike("name","%"+searchMap.get("name")+"%");
            }
            //设置品牌首字母的精确查询
            if (searchMap.get("letter")!=null && !"".equals(searchMap.get("letter"))){
                criteria.andEqualTo("letter",searchMap.get("letter"));
            }
        }
        Page<Brand> pageInfo = (Page<Brand>) brandMapper.selectByExample(example);
        return pageInfo;
    }
}
