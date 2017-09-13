import com.citi.training.spring.jdbc.TestDao;
import com.citi.training.spring.jdbc.TestUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by sxj on 2017/3/2.
 */

@ContextConfiguration("/test-context.xml")
@SqlConfig(dataSource = "datasource")
@RunWith(SpringRunner.class)
public class TestDaoTest {

    @Autowired
    TestDao dao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    @SqlGroup({
            @Sql(scripts = {"classpath:/db/setup.sql", "classpath:/db/setup.sql"},
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:/db/clean.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
    })
    public void testSelectAllCount() {
        assertEquals(2, dao.selectAllCount());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = {"classpath:/db/setup.sql", "classpath:/db/setup.sql"},
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:/db/clean.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
    })
    public void testInsertOneRow() {
        dao.insertOneRow();
        String sql = "SELECT count(*) FROM test_users";
        int count = this.jdbcTemplate.queryForObject(sql, Integer.class);
        assertEquals(3, count);
        TestUser user = selectById(3);
        assertEquals(3, user.getId());
        assertEquals("lily", user.getName());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = {"classpath:/db/setup.sql", "classpath:/db/setup.sql"},
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:/db/clean.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
    })
    public void testDeleteOneRow() {
        dao.deleteOneRow();
        String sql = "SELECT count(*) FROM test_users";
        int count = this.jdbcTemplate.queryForObject(sql, Integer.class);
        assertEquals(1, count);
        assertEquals("hebe", selectById(1).getName());
    }

    private TestUser selectById(int id) {
        String sql = "SELECT id, name FROM test_users where id = ?";
        TestUser user = jdbcTemplate.queryForObject(sql,
                new Object[]{id},
                (RowMapper<TestUser>) new RowMapper<TestUser>() {
                    public TestUser mapRow(ResultSet rs, int rowNum) throws SQLException {
                        TestUser user1 = new TestUser();
                        user1.setId(rs.getInt("id"));
                        user1.setName(rs.getString("name"));
                        return user1;
                    }
                });
        return user;
    }

    @Test
    @SqlGroup({
            @Sql(scripts = {"classpath:/db/setup.sql", "classpath:/db/setup.sql"},
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:/db/clean.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
    })
    public void testUpdateOneRow() {
        dao.updateOneRow();
        TestUser user = selectById(1);
        assertEquals("she", user.getName());
    }


    @Test
    @SqlGroup({
            @Sql(scripts = {"classpath:/db/setup.sql", "classpath:/db/setup.sql"},
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:/db/clean.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
    })
    public void testDeleteAllRows() {
        dao.deleteAllRows();
        String sql = "SELECT count(*) FROM test_users";
        int count = this.jdbcTemplate.queryForObject(sql, Integer.class);
        assertEquals(0, count);
    }


    @Test
    @SqlGroup({
            @Sql(scripts = {"classpath:/db/setup.sql", "classpath:/db/setup.sql"},
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:/db/clean.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
    })
    public void testSelectAllRows() {
        List<TestUser> users = dao.selectAllRows();
        assertEquals(1, users.get(0).getId());
        assertEquals("hebe", users.get(0).getName());
        assertEquals(2, users.get(1).getId());
        assertEquals("jay", users.get(1).getName());
    }


}


