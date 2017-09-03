package com.zhaopin.thrift.tool.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import com.zhaopin.rpc.annotation.ThriftInterface;

@ThriftInterface
@Component
public interface IThriftInvokeReport {

	public boolean registerInvoker(@NotEmpty String invoker, @NotNull List<String> refs);

}
