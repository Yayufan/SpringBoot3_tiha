package tw.com.tiha.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import tw.com.tiha.mapper.ArticleAttachmentMapper;
import tw.com.tiha.pojo.entity.ArticleAttachment;
import tw.com.tiha.service.ArticleAttachmentService;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章的附件 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2024-12-27
 */
@Service
public class ArticleAttachmentServiceImpl extends ServiceImpl<ArticleAttachmentMapper, ArticleAttachment> implements ArticleAttachmentService {

}
