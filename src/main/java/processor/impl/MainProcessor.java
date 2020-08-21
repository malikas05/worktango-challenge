package processor.impl;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import processor.Processor;

import javax.annotation.Nonnull;

public class MainProcessor implements Processor {
    private Processor landingProcessor;
    private Processor instructionProcessor;

    @Inject
    public MainProcessor(@Nonnull @Named("LandingProcessor") Processor landingProcessor, @Nonnull @Named("InstructionProcessor") Processor instructionProcessor) {
        this.landingProcessor = Preconditions.checkNotNull(landingProcessor, "LandingProcessor cannot be null!");
        this.instructionProcessor = Preconditions.checkNotNull(instructionProcessor, "InstructionProcessor cannot be null!");
    }

    @Override
    public void process() {
        landingProcessor.process();
        instructionProcessor.process();
    }
}
