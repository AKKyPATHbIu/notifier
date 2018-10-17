package config;

import db.DBUtil;
import db.TaskUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:notifier.properties")
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = { RootConfig.class })
@ActiveProfiles("DEV")
public class JdbcConfigTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    PlatformTransactionManager txManager;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DBUtil dbUtil;

    @Autowired
    TaskUtil taskUtil;

    @Test
    public void test() {
        assertNotNull(dataSource);
        assertNotNull(txManager);
        assertNotNull(jdbcTemplate);
        assertNotNull(dbUtil);
        assertNotNull(taskUtil);

        assertEquals((Integer) 10, jdbcTemplate.queryForObject("SELECT 10 FROM notifier_tasks LIMIT 1", Integer.class));
    }
}