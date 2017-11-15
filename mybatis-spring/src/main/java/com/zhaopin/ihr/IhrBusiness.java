package com.zhaopin.ihr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by guojun.wang on 2017/11/15.
 */
@Component
public class IhrBusiness {
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BloghistMapper bloghistMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public void addBlog() {
        Blog b = new Blog();
        b.setBlogName("jiangge");
        b.setBlogType(2);
        int count = blogMapper.insert(b);
        Bloghist bh = new Bloghist();
        bh.setBid(b.getId());
        bloghistMapper.insert(bh);
        System.out.println(count);
    }
}
