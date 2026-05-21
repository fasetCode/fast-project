package com.fastproject.system.repository.db;

import com.fastproject.system.domain.SysUsers;
import com.fastproject.system.vo.users.SysUsersVo;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SysUsersRepository extends JpaRepository<SysUsers, Long>, JpaSpecificationExecutor<SysUsers> {
    /**
     * 判断账号是否存在
     */
    boolean existsByUsername(String username);
    /**
     * 判断电话是否存在
     */
    boolean existsByPhone(String phone);
    /**
     * 判断邮箱是否存在
     */
    boolean existsByEmail(String email);

    /**
     * 判断账号是否存在（排除指定ID）
     */
    boolean existsByUsernameAndIdNot(String username, Long id);
    /**
     * 判断电话是否存在（排除指定ID）
     */
    boolean existsByPhoneAndIdNot(String phone, Long id);
    /**
     * 判断邮箱是否存在（排除指定ID）
     */
    boolean existsByEmailAndIdNot(String email, Long id);

    /**
     * 根据ID查询用户（带角色和权限）
     */
    @Query("SELECT u FROM SysUsers u LEFT JOIN FETCH u.roles r LEFT JOIN FETCH r.permissions WHERE u.id = :id")
    Optional<SysUsers> findByIdWithRoles(Long id);

    /**
     *据用户名查询用户
     */
    Optional<SysUsers> findByUsername(String username);


    @Query("SELECT u.id AS id, u.username AS username, u.email AS email," +
            "u.nickname AS nickname,u.phone AS phone,u.gender AS gender," +
            "u.status AS status FROM SysUsers u")
    Page<Tuple> findAllPage(Specification<SysUsers> spec, Pageable pageable);

}
