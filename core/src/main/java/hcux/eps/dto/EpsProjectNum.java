package hcux.eps.dto;

/**Auto Generated By Hap Code Generator**/
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import com.hand.hap.system.dto.BaseDTO;
@ExtensionAttribute(disable=true)
@Table(name = "HCUX_EPS_PROJECT_NUM")
public class EpsProjectNum extends BaseDTO {

     public static final String FIELD_PROJECT_ID = "projectId";
     public static final String FIELD_PROJECT_NUM = "projectNum";


     @Id
     @GeneratedValue
     private Long projectId;

     @Length(max = 200)
     private String projectNum;


     public void setProjectId(Long projectId){
         this.projectId = projectId;
     }

     public Long getProjectId(){
         return projectId;
     }

     public void setProjectNum(String projectNum){
         this.projectNum = projectNum;
     }

     public String getProjectNum(){
         return projectNum;
     }

     }
