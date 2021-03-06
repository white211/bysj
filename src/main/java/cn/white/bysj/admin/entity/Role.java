package cn.white.bysj.admin.entity;



import cn.white.bysj.admin.entity.support.BaseEntity;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author SPPan
 * @since 2016-12-28
 */
@Entity
@Data
@Table(name = "tb_role")
public class Role extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1894163644285296223L;

	/**
	 * 角色id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Integer id;

	/**
	 * 角色名称
	 */
	private String name;

	/**
	 * 角色key
	 */
	private String roleKey;

	/**
	 * 角色状态,0：正常；1：删除
	 */
	private Integer status;

	/**
	 * 角色描述
	 */
	private String description;

	/**
	 * 创建时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	@ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "tb_role_resource", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "resource_id") })
	private java.util.Set<Resource> resources;

}
