package com.zxw.zcloud.demo.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页返回数据实体类
 *
 * @author zouxw
 * @param <T>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {

	private static final long serialVersionUID = -275582248840137389L;

    /**
     * 总数
     */
	private Long count;
    /**
     * 返回码
     */
	private int code;
    /**
     * 当前页结果集
     */
	private List<T> data;
}
