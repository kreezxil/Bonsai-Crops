package com.kreezcraft.bonsaicrops.trees;

import net.minecraft.command.ICommandSender;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import com.kreezcraft.bonsaicrops.BonsaiTrees;
import com.kreezcraft.bonsaicrops.api.IBonsaiIntegration;
import com.kreezcraft.bonsaicrops.api.IBonsaiTreeType;
import com.kreezcraft.bonsaicrops.utility.Logz;

import java.util.Random;

public class ShapeGenerator {
    public static final int NUM_SHAPES = 3;

    private static TreeShape generateShape(World world, BlockPos pos, IBonsaiTreeType type, Random rand) {
        TreeShape result = new TreeShape(type.getName());
        IBonsaiIntegration integrator = BonsaiTrees.instance.typeRegistry.getIntegrationForType(type);
        world.setBlockState(pos.down(), Blocks.GRASS.getDefaultState());
        integrator.generateTree(type, world, pos, rand);

        int heightDiff = 0;
        while(world.isAirBlock(pos) && heightDiff < 8) {
            pos = pos.up();
            heightDiff++;
        }

        result.setBlocksByFloodFill(world, pos);

        integrator.modifyTreeShape(type, result.getBlocks());

        return result;
    }

    public static void generateMissingShapes(ICommandSender sender, World world, BlockPos pos) {
        Random rand = new Random();

        for(IBonsaiTreeType type : BonsaiTrees.instance.typeRegistry.getAllTypes()) {
            int shapes = TreeShapeRegistry.getShapeCountForType(type);
            if (shapes > 0) {
                continue;
            }

            sender.sendMessage(new TextComponentTranslation("commands.bonsaitrees.generateMissingShapes.info", type.getName()));
            Logz.info("Generating shapes for tree: %s", type.getName());

            clearArea(world, pos, 32);
            for(int i = 0; i < NUM_SHAPES; i++) {
                TreeShape treeShape = generateShape(world, pos, type, rand);

                clearArea(world, pos, 32);

                if(treeShape == null) {
                    continue;
                }

                if (treeShape.getBlocks().size() == 0) {
                    sender.sendMessage(new TextComponentTranslation("commands.bonsaitrees.generateMissingShapes.exception.no_shape"));
                    Logz.warn("Can not determine shape");
                    continue;
                }

                String filename = treeShape.saveToFile();
                if(filename != null) {
                    sender.sendMessage(new TextComponentTranslation("commands.bonsaitrees.generateMissingShapes.created_shape_file", filename));
                    Logz.info("Created shape file: %s", filename);
                }
            }
        }
    }


    private static void clearArea(World world, BlockPos pos, int areaSize) {
        for(int deltaX = 0; deltaX < areaSize; deltaX++) {
            for(int deltaY = 0; deltaY < areaSize; deltaY++) {
                for(int deltaZ = 0; deltaZ < areaSize; deltaZ++) {
                    int x = pos.getX() - areaSize / 2;
                    int y = pos.getY();
                    int z = pos.getZ() - areaSize / 2;

                    x += deltaX;
                    y += deltaY;
                    z += deltaZ;

                    world.setBlockToAir(new BlockPos(x, y, z));
                }
            }
        }
    }
}
