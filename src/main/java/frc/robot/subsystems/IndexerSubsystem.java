package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.IndexerConstants;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class IndexerSubsystem extends SubsystemBase {

    public enum IndexerState { FEED, SPIT, STOP }

    private final TalonFX indexerMotor;
    private IndexerState state = IndexerState.STOP;

    public IndexerSubsystem() {
        indexerMotor = new TalonFX(Constants.IndexerConstants.MOTOR_CAN_ID);

        // Current limit
        CurrentLimitsConfigs limits = new CurrentLimitsConfigs();
        limits.StatorCurrentLimit = Constants.IndexerConstants.CURRENT_LIMIT;
        limits.StatorCurrentLimitEnable = true;
        indexerMotor.getConfigurator().apply(limits);

        // Voltage compensation
        VoltageConfigs volts = new VoltageConfigs();
        volts.PeakForwardVoltage = Constants.IndexerConstants.PEAK_VOLTAGE;
        volts.PeakReverseVoltage = -Constants.IndexerConstants.PEAK_VOLTAGE;
        indexerMotor.getConfigurator().apply(volts);

        // Brake mode
        MotorOutputConfigs motcfg = new MotorOutputConfigs()
            .withNeutralMode(NeutralModeValue.Brake)
            .withDutyCycleNeutralDeadband(0.0);
        indexerMotor.getConfigurator().apply(motcfg);
    }

    @Override
    public void periodic() {
        switch (state) {
            case FEED -> setPercentOutput(Constants.IndexerConstants.FEED_SPEED);
            case SPIT -> setPercentOutput(Constants.IndexerConstants.SPIT_SPEED);
            case STOP -> indexerMotor.setControl(new NeutralOut());
        }
    }

    public void setPercentOutput(double percent) {
        DutyCycleOut control = new DutyCycleOut(percent).withEnableFOC(false);//Could play with feild oriented control to ensure the indexer doesnt index unless in positions where robot can shoot.
        indexerMotor.setControl(control);
    }

    public void setState(IndexerState newState) {
        state = newState;
    }

    public IndexerState getState() {
        return state;
    }

}
