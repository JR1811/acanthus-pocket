package net.shirojr.screen.custom;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.shirojr.AcanthusPocket;
import net.shirojr.screen.element.QuickTimeButton;

import java.util.List;
import java.util.function.Supplier;

public class BasicPocketScreen extends HandledScreen<BasicPocketScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(AcanthusPocket.MOD_ID, "textures/screens/basic_pocket_gui.png");
    private final List<ButtonWidget> buttons = Lists.newArrayList();
    private int tick = 0;

    public BasicPocketScreen(BasicPocketScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        int buttonsWidth = 14;
        int buttonsHeight = 10;
        int buttonsX = (this.width / 2) + (backgroundWidth / 2);
        int buttonsY = (this.height / 2) - (backgroundWidth / 2);

        this.buttons.add(this.addDrawableChild(new QuickTimeButton(buttonsX, buttonsY, buttonsWidth, buttonsHeight,
                Text.translatable("gui.acanthes-pocket.basic_pocket_gui"), (button) -> {

            //handler.resetProgress();    // only client side

            if (this.client != null) {
                this.client.interactionManager.clickButton(this.handler.syncId, 0);
                this.close();
            }
        }, Supplier::get)));
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - this.backgroundWidth) / 2;
        int y = (height - this.backgroundHeight) / 2;

        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void handledScreenTick() {
        this.tick = tick + 1;
        if (tick > 100) {
            //TODO: unhide button

            this.tick = 0;
        }
        super.handledScreenTick();
    }

}
