package frc.robot.commands;

import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IndexerSubsystem.IndexerState;
import edu.wpi.first.wpilibj2.command.Command;

public class IndexerCommands {
    //FEED Command
    public static class Feed extends Command {
        private final IndexerSubsystem indexer;

        public Feed(IndexerSubsystem subsystem) {
            this.indexer = subsystem;
            addRequirements(indexer);
        }

        @Override
        public void initialize() {
            indexer.setState(IndexerState.FEED);
        }

        @Override
        public void end(boolean interrupted) {
            indexer.setState(IndexerState.STOP);
        }
    }

    // SPIT COMMAND
    public static class Spit extends Command {
        private final IndexerSubsystem indexer;

        public Spit(IndexerSubsystem subsystem) {
            this.indexer = subsystem;
            addRequirements(indexer);
        }

        @Override
        public void initialize() {
            indexer.setState(IndexerState.SPIT);
        }

        @Override
        public void end(boolean interrupted) {
            indexer.setState(IndexerState.STOP);
        }
    }

    
}
