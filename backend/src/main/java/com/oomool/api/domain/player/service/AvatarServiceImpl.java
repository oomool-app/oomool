package com.oomool.api.domain.player.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oomool.api.domain.player.entity.Avatar;
import com.oomool.api.domain.player.repository.AvatarRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository avatarRepository;

    @Override
    public Avatar getAvatarByPlayerAvatarUrl(String playerAvatarUrl) {
        return avatarRepository.findByUrl(playerAvatarUrl)
            .orElseThrow(() -> new EntityNotFoundException("아바타가 존재하지 않습니다."));
    }
}
