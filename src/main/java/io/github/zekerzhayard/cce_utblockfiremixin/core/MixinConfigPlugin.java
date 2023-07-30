package io.github.zekerzhayard.cce_utblockfiremixin.core;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class MixinConfigPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        for (MethodNode mn : targetClass.methods) {
            if (mn.name.startsWith("redirect$") && mn.name.endsWith("$utBlockFire") && RemapUtils.checkMethodDesc(mn.desc, "(Lnet/minecraft/entity/Entity;I)V")) {
                for (AbstractInsnNode ain : mn.instructions.toArray()) {
                    if (ain.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                        MethodInsnNode min = (MethodInsnNode) ain;
                        if (RemapUtils.checkClassName(min.owner, "net/minecraft/entity/Entity") && RemapUtils.checkMethodName(min.owner, min.name, min.desc, "func_70015_d") && RemapUtils.checkMethodDesc(min.desc, "(I)V")) {
                            mn.instructions.insert(ain, new InsnNode(Opcodes.RETURN));
                            break;
                        }
                    }
                }
            }
        }
    }
}
