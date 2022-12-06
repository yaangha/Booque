package site.book.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.Dto.ReplyReadDto;
import site.book.project.Dto.ReplyRegisterDto;
import site.book.project.domain.Post;
import site.book.project.domain.PostReply;
import site.book.project.domain.User;
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
    @Transactional
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
        User user = userRepository.findByName(dto.getReplyWriter());
        
        PostReply reply = PostReply.builder().post(post)
                .replyContent(dto.getReplyContent()).user(user)
                .build();
        
        reply = replyRepository.save(reply);
        return reply.getReplyId();
    }

}
