package net.shirojr.screen.custom;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.shirojr.AcanthusPocket;
import net.shirojr.screen.element.QuickTimeButton;

import java.util.List;
import java.util.function.Supplier;

public class BasicPocketScreen extends HandledScreen<BasicPocketScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(AcanthusPocket.MOD_ID, "textures/screens/basic_pocket_gui.png");
    private final List<ButtonWidget> buttons = Lists.newArrayList();
    private int tick = 0;
    private PlayerInventory inventory;

    public BasicPocketScreen(BasicPocketScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.inventory = inventory;
    }

    @Override
    protected void init() {
        super.init();

        this.backgroundWidth = 175;
        this.backgroundHeight = 81;

        this.titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        this.titleY = 49;
        this.playerInventoryTitleX = 7;
        this.playerInventoryTitleY = this.backgroundHeight + 7;

        addNewButton(getButtonPosition()[0], getButtonPosition()[1]);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (this.width / 2) - (backgroundWidth / 2);
        int y = (this.height / 2) - (backgroundHeight / 2);

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
        int invisibleTicks = 20;
        int visibleTicks = 40;

        this.tick++;

        if (tick < invisibleTicks) {
            this.buttons.get(0).visible = false;
        } else if (tick == invisibleTicks) {
            if (getButtonPosition() != null) {
                this.buttons.remove(0);
                addNewButton(getButtonPosition()[0], getButtonPosition()[1]);
            }

            this.buttons.get(0).visible = true;
            //tick = 0;
        }

        if (tick > invisibleTicks + visibleTicks) {
            if (this.inventory.player.getWorld().isClient) {
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeUuid(inventory.player.getUuid());
                ClientPlayNetworking.send(AcanthusPocket.FAILED_QUICK_TIME_PACKET_ID, buf);
            }


            tick = 0;
            //this.close();
        }

        super.handledScreenTick();
    }

    private int[] getButtonPosition() {
        if (MinecraftClient.getInstance().player == null) return null;

        Random random = MinecraftClient.getInstance().player.getWorld().getRandom();
        int buttonMargin = 5;
        int[] output = new int[2];

        if (random.nextBoolean()) {
            output[0] = (this.width / 2) + (backgroundWidth / 2) + buttonMargin;
        } else {
            output[0] = (this.width / 2) - (backgroundWidth / 2) - (buttonMargin + 10);
        }

        output[1] = random.nextBetween((this.height / 2) - (backgroundHeight / 2), (this.height / 2) + (backgroundHeight / 2) - 14);

        //output[0] = random.nextBoolean() ? 180 : 0;
        //output[1] = random.nextInt(52);
        return output;
    }

    /**
     * Deletes old version of the botton and creates a new one
     *
     * @param x X value of the button
     * @param y Y value of the button
     */
    private void addNewButton(int x, int y) {
        this.buttons.clear();
        this.buttons.add(this.addDrawableChild(new QuickTimeButton(x, y,
                Text.translatable("gui.acanthes-pocket.basic_pocket_gui"), (button) -> {

            if (this.client != null) {
                tick = 0;
                this.client.interactionManager.clickButton(this.handler.syncId, 0);
                this.buttons.get(0).visible = false;
                //this.close();
            }
        }, Supplier::get)));
    }
}
