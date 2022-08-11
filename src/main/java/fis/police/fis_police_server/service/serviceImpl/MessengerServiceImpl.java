package fis.police.fis_police_server.service.serviceImpl;

import fis.police.fis_police_server.domain.Messenger;
import fis.police.fis_police_server.domain.User;
import fis.police.fis_police_server.domain.enumType.UserAuthority;
import fis.police.fis_police_server.repository.MessengerRepository;
import fis.police.fis_police_server.service.MessengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MessengerServiceImpl implements MessengerService {

    private final MessengerRepository messengerRepository;

    @Override
    public void saveMessenger(Messenger messenger) {
        messengerRepository.save(messenger);
    }

    @Override
    public void deleteMessenger(Long id) {
        messengerRepository.delete(id);
    }

    @Override
    public List<Messenger> getMessenger(User user) {
        if(user.getU_auth() == UserAuthority.ADMIN) {
            return messengerRepository.findAll();
        } else {
            return messengerRepository.findByUser(user);
        }
    }
}
