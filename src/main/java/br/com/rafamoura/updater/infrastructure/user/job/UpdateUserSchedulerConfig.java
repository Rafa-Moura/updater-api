package br.com.rafamoura.updater.infrastructure.user.job;

import br.com.rafamoura.updater.application.user.abstractions.scheduler.UpdateUserScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserSchedulerConfig {

    private final UpdateUserScheduler updateUserScheduler;

    @Scheduled(fixedRate = 3000000)
    public void executeJob() {
        updateUserScheduler.updateUserJob();
    }
}