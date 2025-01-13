package tw.org.organ.pojo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import tw.org.organ.utils.TreeNode;

/**
 * <p>
 * 文章的細部類別
 * </p>
 *
 * @author Joey
 * @since 2024-09-23
 */
@Getter
@Setter
@TableName("article_category")
@Schema(name = "ArticleCategory", description = "文章的細部類別")
public class ArticleCategory implements Serializable,TreeNode<ArticleCategory, Long> {

    private static final long serialVersionUID = 1L;

    @Schema(description = "類別 ID")
    @TableId("article_category_id")
    private Long articleCategoryId;

    @Schema(description = "文章的組別")
    @TableField("group_type")
    private String groupType;

    @Schema(description = "類別名稱")
    @TableField("name")
    private String name;

    @Schema(description = "用於描述這個類別主要放的是什麼")
    @TableField("description")
    private String description;

    @Schema(description = "父類別ID")
    @TableField("parent_id")
    private Long parentId;

    @Schema(description = "創建時間")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "創建者")
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    @Schema(description = "最後更新時間")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "最後更改者")
    @TableField(value = "update_by",fill = FieldFill.UPDATE )
    private String updateBy;

    @Schema(description = "邏輯刪除(0為存在,1為刪除)")
    @TableField("is_deleted")
    @TableLogic
    private String isDeleted;
    
    /**
	 * 為了整理Catrgory嵌套關係這邊實現自定義的NodeTree接口,並實現一些額外方法 getId()方法, 是要實現NodeTree接口的方法,
	 * 但要使用JsonIgnore來忽略,不然之後輸出結果會多一個id
	 */

	// 創建一個children用來獲取子類別,並且聲明這是一個不存在於數據的字段
	@TableField(exist = false)
	@Schema(description = "子類別")
	private List<ArticleCategory> children;

	@Override
	@JsonIgnore
	public Long getId() {
		// TODO Auto-generated method stub
		return getArticleCategoryId();
	}

	@Override
	public void setChildrenInternal(List<ArticleCategory> children) {
		// TODO Auto-generated method stub
		this.children = children;

	}

    
    
    
}
