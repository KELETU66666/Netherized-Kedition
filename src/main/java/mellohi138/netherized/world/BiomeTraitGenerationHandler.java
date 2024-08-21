/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package mellohi138.netherized.world;

import git.jbredwards.nether_api.api.event.NetherAPIFogColorEvent;
import git.jbredwards.nether_api.api.event.NetherAPIRegistryEvent;
import mellohi138.netherized.Netherized;
import mellohi138.netherized.init.NetherizedBiomes;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Netherized.MODID)
public class BiomeTraitGenerationHandler
{
    @SubscribeEvent
    public static void onPreBiomeDecorate(NetherAPIRegistryEvent.Nether event)
    {
        event.registry.registerBiome(NetherizedBiomes.CRIMSON_FOREST, 5);
        event.registry.registerBiome(NetherizedBiomes.WARPED_FOREST, 5);
    }

    @SubscribeEvent
    public static void onPreBiomeDecorate(NetherAPIFogColorEvent event)
    {
        if(event.biome == NetherizedBiomes.WARPED_FOREST) {
            event.fogR = 25;
            event.fogG = 6;
            event.fogB = 25;
        }
    }
}