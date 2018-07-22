package net.md_5.bungee.protocol;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.protocol.packet.BossBar;
import net.md_5.bungee.protocol.packet.Chat;
import net.md_5.bungee.protocol.packet.ClientSettings;
import net.md_5.bungee.protocol.packet.EncryptionRequest;
import net.md_5.bungee.protocol.packet.EncryptionResponse;
import net.md_5.bungee.protocol.packet.Handshake;
import net.md_5.bungee.protocol.packet.KeepAlive;
import net.md_5.bungee.protocol.packet.Kick;
import net.md_5.bungee.protocol.packet.Login;
import net.md_5.bungee.protocol.packet.LoginPayloadRequest;
import net.md_5.bungee.protocol.packet.LoginPayloadResponse;
import net.md_5.bungee.protocol.packet.LoginRequest;
import net.md_5.bungee.protocol.packet.LoginSuccess;
import net.md_5.bungee.protocol.packet.PingPacket;
import net.md_5.bungee.protocol.packet.PlayerListHeaderFooter;
import net.md_5.bungee.protocol.packet.PlayerListItem;
import net.md_5.bungee.protocol.packet.PluginMessage;
import net.md_5.bungee.protocol.packet.Respawn;
import net.md_5.bungee.protocol.packet.ScoreboardDisplay;
import net.md_5.bungee.protocol.packet.ScoreboardObjective;
import net.md_5.bungee.protocol.packet.ScoreboardScore;
import net.md_5.bungee.protocol.packet.SetCompression;
import net.md_5.bungee.protocol.packet.StatusRequest;
import net.md_5.bungee.protocol.packet.StatusResponse;
import net.md_5.bungee.protocol.packet.TabCompleteRequest;
import net.md_5.bungee.protocol.packet.TabCompleteResponse;
import net.md_5.bungee.protocol.packet.Team;
import net.md_5.bungee.protocol.packet.Title;

public enum Protocol
{

    // Undef
    HANDSHAKE
    {

        {
            TO_SERVER.registerPacket(
                    Handshake.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x00 )
            );
        }
    },
    // 0
    GAME
    {

        {
            TO_CLIENT.registerPacket(
                    KeepAlive.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x00 ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x1F ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x1F ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x21 )
            );
            TO_CLIENT.registerPacket(
                    Login.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x01 ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x23 ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x23 ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x25 )
            );
            TO_CLIENT.registerPacket(
                    Chat.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x02 ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x0F ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x0F ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x0E )
            );
            TO_CLIENT.registerPacket(
                    Respawn.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x07 ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x33 ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x34 ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x35 ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x38 )
            );
            TO_CLIENT.registerPacket(
                    BossBar.class,
                    map( ProtocolConstants.MINECRAFT_1_9, 0x0C ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x0C ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x0C )
            );
            TO_CLIENT.registerPacket(
                    PlayerListItem.class, // PlayerInfo
                    map( ProtocolConstants.MINECRAFT_1_8, 0x38 ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x2D ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x2D ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x2E ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x30 )
            );
            TO_CLIENT.registerPacket(
                    TabCompleteResponse.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x3A ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x0E ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x0E ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x10 )
            );
            TO_CLIENT.registerPacket(
                    ScoreboardObjective.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x3B ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x3F ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x41 ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x42 ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x45 )
            );
            TO_CLIENT.registerPacket(
                    ScoreboardScore.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x3C ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x42 ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x44 ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x45 ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x48 )
            );
            TO_CLIENT.registerPacket(
                    ScoreboardDisplay.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x3D ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x38 ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x3A ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x3B ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x3E )
            );
            TO_CLIENT.registerPacket(
                    Team.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x3E ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x41 ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x43 ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x44 ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x47 )
            );
            TO_CLIENT.registerPacket(
                    PluginMessage.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x3F ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x18 ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x18 ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x19 )
            );
            TO_CLIENT.registerPacket(
                    Kick.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x40 ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x1A ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x1A ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x1B )
            );
            TO_CLIENT.registerPacket(
                    Title.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x45 ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x47 ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x48 ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x4B )
            );
            TO_CLIENT.registerPacket(
                    PlayerListHeaderFooter.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x47 ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x48 ),
                    map( ProtocolConstants.MINECRAFT_1_9_4, 0x47 ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x49 ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x4A ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x4E )
            );

            TO_SERVER.registerPacket(
                    KeepAlive.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x00 ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x0B ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x0C ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x0B ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x0E )
            );
            TO_SERVER.registerPacket(
                    Chat.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x01 ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x02 ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x03 ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x02 ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x02 )
            );
            TO_SERVER.registerPacket(
                    TabCompleteRequest.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x14 ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x01 ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x02 ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x01 ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x05 )
            );
            TO_SERVER.registerPacket(
                    ClientSettings.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x15 ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x04 ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x05 ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x04 ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x04 )
            );
            TO_SERVER.registerPacket(
                    PluginMessage.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x17 ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x09 ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x0A ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x09 ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x0A )
            );
        }
    },
    // 1
    STATUS
    {

        {
            TO_CLIENT.registerPacket(
                    StatusResponse.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x00 )
            );
            TO_CLIENT.registerPacket(
                    PingPacket.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x01 )
            );

            TO_SERVER.registerPacket(
                    StatusRequest.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x00 )
            );
            TO_SERVER.registerPacket(
                    PingPacket.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x01 )
            );
        }
    },
    //2
    LOGIN
    {

        {
            TO_CLIENT.registerPacket(
                    Kick.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x00 )
            );
            TO_CLIENT.registerPacket(
                    EncryptionRequest.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x01 )
            );
            TO_CLIENT.registerPacket(
                    LoginSuccess.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x02 )
            );
            TO_CLIENT.registerPacket(
                    SetCompression.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x03 )
            );
            TO_CLIENT.registerPacket(
                    LoginPayloadRequest.class,
                    map( ProtocolConstants.MINECRAFT_1_13, 0x04 )
            );

            TO_SERVER.registerPacket(
                    LoginRequest.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x00 )
            );
            TO_SERVER.registerPacket(
                    EncryptionResponse.class,
                    map( ProtocolConstants.MINECRAFT_1_8, 0x01 )
            );
            TO_SERVER.registerPacket(
                    LoginPayloadResponse.class,
                    map( ProtocolConstants.MINECRAFT_1_13, 0x02 )
            );
        }
    };
    /*========================================================================*/
    public static final int MAX_PACKET_ID = 0xFF;
    /*========================================================================*/
    public final DirectionData TO_SERVER = new DirectionData( this, ProtocolConstants.Direction.TO_SERVER );
    public final DirectionData TO_CLIENT = new DirectionData( this, ProtocolConstants.Direction.TO_CLIENT );

    public static void main(String[] args)
    {
        for ( int version : ProtocolConstants.SUPPORTED_VERSION_IDS )
        {
            dump( version );
        }
    }

    private static void dump(int version)
    {
        for ( Protocol protocol : Protocol.values() )
        {
            dump( version, protocol );
        }
    }

    private static void dump(int version, Protocol protocol)
    {
        dump( version, protocol.TO_CLIENT );
        dump( version, protocol.TO_SERVER );
    }

    private static void dump(int version, DirectionData data)
    {
        for ( int id = 0; id < MAX_PACKET_ID; id++ )
        {
            DefinedPacket packet = data.createPacket( id, version );
            if ( packet != null )
            {
                System.out.println( version + " " + data.protocolPhase + " " + data.direction + " " + id + " " + packet.getClass().getSimpleName() );
            }
        }
    }

    @RequiredArgsConstructor
    private static class ProtocolData
    {

        private final int protocolVersion;
        private final TObjectIntMap<Class<? extends DefinedPacket>> packetMap = new TObjectIntHashMap<>( MAX_PACKET_ID );
        private final Constructor<? extends DefinedPacket>[] packetConstructors = new Constructor[ MAX_PACKET_ID ];
    }

    @RequiredArgsConstructor
    private static class ProtocolMapping
    {

        private final int protocolVersion;
        private final int packetID;
    }

    // Helper method
    private static ProtocolMapping map(int protocol, int id)
    {
        return new ProtocolMapping( protocol, id );
    }

    @RequiredArgsConstructor
    public static class DirectionData
    {

        private final Protocol protocolPhase;
        private final TIntObjectMap<ProtocolData> protocols = new TIntObjectHashMap<>();
        {
            for ( int protocol : ProtocolConstants.SUPPORTED_VERSION_IDS )
            {
                protocols.put( protocol, new ProtocolData( protocol ) );
            }
        }
        private final TIntObjectMap<List<Integer>> linkedProtocols = new TIntObjectHashMap<>();
        {
            linkedProtocols.put( ProtocolConstants.MINECRAFT_1_8, Arrays.asList(
                    ProtocolConstants.MINECRAFT_1_9,
                    ProtocolConstants.MINECRAFT_1_12,
                    ProtocolConstants.MINECRAFT_1_13
            ) );
            linkedProtocols.put( ProtocolConstants.MINECRAFT_1_9, Arrays.asList(
                    ProtocolConstants.MINECRAFT_1_9_1,
                    ProtocolConstants.MINECRAFT_1_9_2,
                    ProtocolConstants.MINECRAFT_1_9_4
            ) );
            linkedProtocols.put( ProtocolConstants.MINECRAFT_1_9_4, Arrays.asList(
                    ProtocolConstants.MINECRAFT_1_10,
                    ProtocolConstants.MINECRAFT_1_11,
                    ProtocolConstants.MINECRAFT_1_11_1
            ) );
            linkedProtocols.put( ProtocolConstants.MINECRAFT_1_12, Arrays.asList(
                    ProtocolConstants.MINECRAFT_1_12_1
            ) );
            linkedProtocols.put( ProtocolConstants.MINECRAFT_1_12_1, Arrays.asList(
                    ProtocolConstants.MINECRAFT_1_12_2
            ) );
        }

        @Getter
        private final ProtocolConstants.Direction direction;

        private ProtocolData getProtocolData(int version)
        {
            ProtocolData protocol = protocols.get( version );
            if ( protocol == null && ( protocolPhase != Protocol.GAME ) )
            {
                protocol = Iterables.getFirst( protocols.valueCollection(), null );
            }
            return protocol;
        }

        public final DefinedPacket createPacket(int id, int version)
        {
            ProtocolData protocolData = getProtocolData( version );
            if ( protocolData == null )
            {
                return null; // Unsupported protocol version
            }
            if ( id > MAX_PACKET_ID )
            {
                throw new BadPacketException( "Packet with id " + id + " outside of range " );
            }

            Constructor<? extends DefinedPacket> constructor = protocolData.packetConstructors[id];
            try
            {
                return ( constructor == null ) ? null : constructor.newInstance();
            } catch ( ReflectiveOperationException ex )
            {
                throw new BadPacketException( "Could not construct packet with id " + id, ex );
            }
        }

        protected final void registerPacket(Class<? extends DefinedPacket> packetClass, ProtocolMapping... mappings)
        {
            try
            {
                Constructor<? extends DefinedPacket> constructor = packetClass.getDeclaredConstructor();
                for ( ProtocolMapping mapping : mappings )
                {
                    ProtocolData data = protocols.get( mapping.protocolVersion );
                    data.packetMap.put( packetClass, mapping.packetID );
                    data.packetConstructors[mapping.packetID] = constructor;

                    List<Integer> links = linkedProtocols.get( mapping.protocolVersion );
                    if ( links != null )
                    {
                        links: for ( int link : links )
                        {
                            // Check for manual mappings
                            for ( ProtocolMapping m : mappings )
                            {
                                if ( m == mapping ) continue;
                                if ( m.protocolVersion == link ) continue links;
                                List<Integer> innerLinks = linkedProtocols.get( m.protocolVersion );
                                if ( innerLinks != null && innerLinks.contains( link ) ) continue links;
                            }
                            registerPacket( packetClass, map( link, mapping.packetID ) );
                        }
                    }
                }
            } catch ( NoSuchMethodException ex )
            {
                throw new BadPacketException( "No NoArgsConstructor for packet class " + packetClass );
            }
        }

        final int getId(Class<? extends DefinedPacket> packet, int version)
        {

            ProtocolData protocolData = getProtocolData( version );
            if ( protocolData == null )
            {
                throw new BadPacketException( "Unsupported protocol version" );
            }
            Preconditions.checkArgument( protocolData.packetMap.containsKey( packet ), "Cannot get ID for packet %s in phase %s with direction %s", packet, protocolPhase, direction );

            return protocolData.packetMap.get( packet );
        }
    }
}
