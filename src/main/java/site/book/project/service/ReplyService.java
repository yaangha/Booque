package site.book.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Post;
import site.book.project.domain.PostReply;
import site.book.project.dto.ReplyReadDto;
import site.book.project.dto.ReplyRegisterDto;
import site.book.project.dto.ReplyUpdateDto;

import site.book.project.repository.PostRepository;
import site.book.project.repository.ReplyRepository;
import site.book.project.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    
    // 포스트에 해당하는 댓글 리스트 리턴

    @Transactional(readOnly = true)

    public List<ReplyReadDto> readReplies(Integer postId) {
        log.info("readReplies(postId={})", postId);
        
        List<PostReply> list = replyRepository.readAllReplies(postId);
        
        return list.stream()
                .map(ReplyReadDto::fromEntity)
                .toList();
    }

    // DB에 댓글 정보 저장
    public Integer create(ReplyRegisterDto dto) {
        log.info("create(dto={})", dto);
        
        Post post = postRepository.findById(dto.getPostId()).get();
        
        
        // 유저 정보에 저장하기 위해서 작성자 정보를 따로 저장
        // User user = userRepository.findByName(dto.getReplyWriter());
        
        PostReply reply = PostReply.builder().post(post)
                .replyContent(dto.getReplyContent()).replyWriter(dto.getReplyWriter())
                .build();
        
        reply = replyRepository.save(reply);
        return reply.getReplyId();
    }

    @Transactional(readOnly = true)
    public ReplyReadDto readReply(Integer replyId) {
        log.info("readReply(replyId={})", replyId);
        PostReply entity = replyRepository.findById(replyId).get();
        return ReplyReadDto.fromEntity(entity);
    }

    @Transactional
    public Integer delete(Integer replyId) {
        log.info("delete(replyId={})", replyId);
        replyRepository.deleteById(replyId);
        return replyId;
    }

    @Transactional
    public Integer update(ReplyUpdateDto dto) {
        log.info("update(dto={})", dto);
        PostReply entity = replyRepository.findById(dto.getReplyId()).get();
        log.info(dto.getReplyContent());
        entity.update(dto.getReplyContent());
        return entity.getReplyId();
    }
}

