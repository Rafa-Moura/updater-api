package br.com.rafamoura.updater.application.user.scheduler;

import br.com.rafamoura.updater.application.user.abstractions.scheduler.UpdateUserScheduler;
import br.com.rafamoura.updater.domain.user.abstractions.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UpdateUserSchedulerImpl implements UpdateUserScheduler {

    private final UserService userService;

    @Override
    public void updateUserJob() {
        log.info("[UpdateUserSchedulerImpl.updateUserJob] - Starting job");

        userService.findUsers();

        log.info("[UpdateUserSchedulerImpl.updateUserJob] - Finishing job");
    }
}