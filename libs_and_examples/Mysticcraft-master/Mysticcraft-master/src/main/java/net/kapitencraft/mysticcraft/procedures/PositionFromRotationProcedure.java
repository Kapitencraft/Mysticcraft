package net.kapitencraft.mysticcraft.procedures;

import org.checkerframework.checker.units.qual.C;

import net.minecraft.world.entity.Entity;

import net.kapitencraft.mysticcraft.MysticcraftMod;

public class PositionFromRotationProcedure {
	public static void execute(double x, Entity entity) {
		if (entity == null)
			return;
		double B = 0;
		double C = 0;
		double reach = 0;
		double zFinal = 0;
		double pitch = 0;
		double yFinal = 0;
		double yaw = 0;
		double xFinal = 0;
		reach = x;
		yaw = entity.getYRot();
		pitch = entity.getXRot();
		if (pitch == -90) {
			yFinal = entity.getY() + reach;
		} else if (pitch == 90) {
			yFinal = entity.getY() - reach;
		} else if (pitch == 0) {
			yFinal = entity.getY();
		} else if (pitch > -90 && pitch < 0) {
			B = pitch + 90;
			C = (Math.PI / 180) * (90 - B);
			B = (Math.PI / 180) * B;
			yFinal = entity.getY() + (reach * Math.sin(B)) / Math.sin(90);
			reach = reach - (reach * Math.sin(C)) / Math.sin(90);
		} else {
			B = pitch * (-1);
			C = (Math.PI / 180) * (90 - B);
			B = (Math.PI / 180) * B;
			yFinal = entity.getY() + (reach * Math.sin(B)) / Math.sin(90);
			reach = reach - (reach * Math.sin(C)) / Math.sin(90);
		}
		if (!(pitch == -90 || pitch == 90)) {
			if (yaw == -90) {
				xFinal = entity.getX() + reach;
				zFinal = entity.getZ();
			} else if (yaw == -180) {
				xFinal = entity.getX();
				zFinal = entity.getZ() - reach;
			} else if (yaw == 90) {
				xFinal = entity.getX() - reach;
				zFinal = entity.getZ();
			} else if (yaw == 0) {
				xFinal = entity.getX();
				zFinal = entity.getZ() + reach;
			}
			if (yaw > 90 && yaw <= 179) {
				B = yaw - 90;
				C = (Math.PI / 180) * (90 - B);
				B = (Math.PI / 180) * B;
				xFinal = entity.getX() - (reach * Math.sin(B)) / Math.sin(Math.PI / 2);
				zFinal = entity.getZ() - (reach * Math.sin(C)) / Math.sin(Math.PI / 2);
			} else if (yaw > 0 && yaw < 90) {
				B = yaw;
				C = (Math.PI / 180) * (90 - B);
				B = (Math.PI / 180) * B;
				xFinal = entity.getX() - (reach * Math.sin(B)) / Math.sin(Math.PI / 2);
				zFinal = entity.getZ() + (reach * Math.sin(C)) / Math.sin(Math.PI / 2);
			} else if (yaw > -90 && yaw < 0) {
				B = yaw + 90;
				C = (Math.PI / 180) * (90 - B);
				B = (Math.PI / 180) * B;
				xFinal = entity.getX() + (reach * Math.sin(B)) / Math.sin(Math.PI / 2);
				zFinal = entity.getZ() + (reach * Math.sin(C)) / Math.sin(Math.PI / 2);
			} else if (yaw >= -179 && yaw < -90) {
				B = yaw + 180;
				C = (Math.PI / 180) * (90 - B);
				B = (Math.PI / 180) * B;
				xFinal = entity.getX() - (reach * Math.sin(B)) / Math.sin(Math.PI / 2);
				zFinal = entity.getZ() - (reach * Math.sin(C)) / Math.sin(Math.PI / 2);
			}
		}
		MysticcraftMod.LOGGER.debug(("Generated Code from [" + (entity.getX() + ", " + entity.getY() + ", " + entity.getZ()) + "] to ["
				+ (xFinal + ", " + yFinal + ", " + zFinal) + "]"));
	}
}
