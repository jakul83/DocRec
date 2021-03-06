USE [master]
GO
/****** Object:  Database [wierzytelnosci]    Script Date: 2019-04-28 12:50:32 ******/
CREATE DATABASE [wierzytelnosci]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'wierzytelnosci', FILENAME = N'C:\Program Files (x86)\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\wierzytelnosci.mdf' , SIZE = 10240KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'wierzytelnosci_log', FILENAME = N'C:\Program Files (x86)\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\wierzytelnosci_log.ldf' , SIZE = 6272KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [wierzytelnosci] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [wierzytelnosci].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [wierzytelnosci] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [wierzytelnosci] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [wierzytelnosci] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [wierzytelnosci] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [wierzytelnosci] SET ARITHABORT OFF 
GO
ALTER DATABASE [wierzytelnosci] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [wierzytelnosci] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [wierzytelnosci] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [wierzytelnosci] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [wierzytelnosci] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [wierzytelnosci] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [wierzytelnosci] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [wierzytelnosci] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [wierzytelnosci] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [wierzytelnosci] SET  DISABLE_BROKER 
GO
ALTER DATABASE [wierzytelnosci] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [wierzytelnosci] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [wierzytelnosci] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [wierzytelnosci] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [wierzytelnosci] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [wierzytelnosci] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [wierzytelnosci] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [wierzytelnosci] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [wierzytelnosci] SET  MULTI_USER 
GO
ALTER DATABASE [wierzytelnosci] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [wierzytelnosci] SET DB_CHAINING OFF 
GO
ALTER DATABASE [wierzytelnosci] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [wierzytelnosci] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [wierzytelnosci] SET DELAYED_DURABILITY = DISABLED 
GO
USE [wierzytelnosci]
GO
/****** Object:  Table [dbo].[adres_dluznika]    Script Date: 2019-04-28 12:50:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[adres_dluznika](
	[ID_adres_dluznika] [int] NOT NULL,
	[ulica] [varchar](50) NOT NULL,
	[nr_lokalu] [varchar](10) NOT NULL,
	[kod_pocztowy] [int] NOT NULL,
	[miasto] [varchar](50) NOT NULL,
	[status] [varchar](10) NOT NULL,
	[id_dluznika] [int] NOT NULL,
 CONSTRAINT [PK_adres_dluznika] PRIMARY KEY CLUSTERED 
(
	[ID_adres_dluznika] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[adres_wierzyciela]    Script Date: 2019-04-28 12:50:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[adres_wierzyciela](
	[ID_adres_wierzyciela] [int] NOT NULL,
	[Ulica] [varchar](50) NOT NULL,
	[Nr_lokalu] [varchar](10) NOT NULL,
	[Kod_pocztowy] [int] NOT NULL,
	[Miasto] [varchar](50) NOT NULL,
 CONSTRAINT [PK_adres_wierzyciela] PRIMARY KEY CLUSTERED 
(
	[ID_adres_wierzyciela] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[dluznicy]    Script Date: 2019-04-28 12:50:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[dluznicy](
	[ID_dluznika] [int] NOT NULL,
	[imie] [varchar](50) NOT NULL,
	[nazwisko] [varchar](50) NOT NULL,
	[PESEL] [char](10) NOT NULL,
	[nr_dowodu_os] [varchar](10) NULL,
 CONSTRAINT [PK_dluznicy] PRIMARY KEY CLUSTERED 
(
	[ID_dluznika] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[dokument]    Script Date: 2019-04-28 12:50:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[dokument](
	[ID_dokument] [int] NOT NULL,
	[Date] [date] NULL,
	[Opis] [varchar](max) NULL,
	[ID_wierzytelnosci] [int] NOT NULL,
 CONSTRAINT [PK_dokument] PRIMARY KEY CLUSTERED 
(
	[ID_dokument] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[powiazania]    Script Date: 2019-04-28 12:50:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[powiazania](
	[ID_powiazania] [int] NOT NULL,
	[ID_wierzytelnosci] [int] NOT NULL,
	[ID_dluznika] [int] NOT NULL,
 CONSTRAINT [PK_powiazania] PRIMARY KEY CLUSTERED 
(
	[ID_powiazania] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[skan]    Script Date: 2019-04-28 12:50:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[skan](
	[ID_skan] [int] NOT NULL,
	[Skan] [image] NOT NULL,
	[ID_dokument] [int] NOT NULL,
 CONSTRAINT [PK_skan] PRIMARY KEY CLUSTERED 
(
	[ID_skan] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[skany_grupy]    Script Date: 2019-04-28 12:50:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[skany_grupy](
	[sk_grupy] [int] IDENTITY(1,1) NOT NULL,
	[nazwa] [varchar](50) NOT NULL,
	[skan] [varbinary](max) NOT NULL,
	[data_skanu] [smalldatetime] NOT NULL,
	[dzial] [varchar](50) NOT NULL,
	[strony] [nchar](10) NOT NULL,
 CONSTRAINT [PK_skany_grupy] PRIMARY KEY CLUSTERED 
(
	[sk_grupy] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[wierzyciele]    Script Date: 2019-04-28 12:50:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[wierzyciele](
	[Wierzyciel] [varchar](50) NOT NULL,
	[NIP] [int] NOT NULL,
	[KRS] [int] NOT NULL,
	[ID_adres_wierzyciela] [int] NULL,
	[ID_wierzyciel] [int] IDENTITY(1,1) NOT NULL,
 CONSTRAINT [PK_wierzyciele] PRIMARY KEY CLUSTERED 
(
	[ID_wierzyciel] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[wierzytelnosci]    Script Date: 2019-04-28 12:50:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[wierzytelnosci](
	[ID_wierzytelnosci] [int] NOT NULL,
	[Rodzaj] [varchar](25) NOT NULL,
	[Nr_umowy] [int] NOT NULL,
	[Data_umowy] [int] NOT NULL,
	[Kwota] [decimal](10, 2) NOT NULL,
	[Saldo] [decimal](10, 2) NOT NULL,
	[Okres] [int] NOT NULL,
	[Data_wymagalnosci] [date] NOT NULL,
	[ID_wierzyciela] [int] NOT NULL,
 CONSTRAINT [PK_wierzytelnosci] PRIMARY KEY CLUSTERED 
(
	[ID_wierzytelnosci] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[wplaty]    Script Date: 2019-04-28 12:50:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[wplaty](
	[ID_wplaty] [int] NOT NULL,
	[Date] [date] NOT NULL,
	[Kwota] [decimal](10, 2) NOT NULL,
	[ID_wierzytelnosci] [int] NOT NULL,
 CONSTRAINT [PK_wplaty] PRIMARY KEY CLUSTERED 
(
	[ID_wplaty] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  View [dbo].[Wierzyciele_wierzytelnosci_wplaty]    Script Date: 2019-04-28 12:50:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[Wierzyciele_wierzytelnosci_wplaty]
AS
SELECT        dbo.wierzyciele.ID_wierzyciela, dbo.wierzyciele.Wierzyciel, dbo.wierzytelnosci.ID_wierzytelnosci, dbo.wierzytelnosci.Data_umowy, dbo.wierzytelnosci.Kwota, 
                         dbo.wierzytelnosci.Saldo, dbo.wplaty.ID_wplaty, dbo.wplaty.Date, dbo.wplaty.Kwota AS Expr1
FROM            dbo.wierzyciele INNER JOIN
                         dbo.wierzytelnosci ON dbo.wierzyciele.ID_wierzyciela = dbo.wierzytelnosci.ID_wierzyciela INNER JOIN
                         dbo.wplaty ON dbo.wierzytelnosci.ID_wierzytelnosci = dbo.wplaty.ID_wierzytelnosci
GROUP BY dbo.wierzyciele.ID_wierzyciela, dbo.wierzyciele.Wierzyciel, dbo.wierzytelnosci.ID_wierzytelnosci, dbo.wierzytelnosci.Data_umowy, dbo.wierzytelnosci.Kwota, 
                         dbo.wierzytelnosci.Saldo, dbo.wplaty.ID_wplaty, dbo.wplaty.Date, dbo.wplaty.Kwota

GO
/****** Object:  View [dbo].[Wierzytelnosci_dluznicy_wplaty]    Script Date: 2019-04-28 12:50:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[Wierzytelnosci_dluznicy_wplaty]
AS
SELECT        dbo.wierzytelnosci.ID_wierzytelnosci, dbo.wierzytelnosci.Rodzaj, dbo.dluznicy.ID_dluznika, dbo.dluznicy.imie, dbo.dluznicy.nazwisko, dbo.wplaty.Date, 
                         dbo.wplaty.Kwota
FROM            dbo.wplaty INNER JOIN
                         dbo.wierzytelnosci ON dbo.wplaty.ID_wierzytelnosci = dbo.wierzytelnosci.ID_wierzytelnosci INNER JOIN
                         dbo.powiazania ON dbo.wierzytelnosci.ID_wierzytelnosci = dbo.powiazania.ID_wierzytelnosci INNER JOIN
                         dbo.dluznicy ON dbo.powiazania.ID_dluznika = dbo.dluznicy.ID_dluznika
GROUP BY dbo.wierzytelnosci.ID_wierzytelnosci, dbo.wierzytelnosci.Rodzaj, dbo.dluznicy.ID_dluznika, dbo.dluznicy.imie, dbo.dluznicy.nazwisko, dbo.wplaty.Date, 
                         dbo.wplaty.Kwota

GO
/****** Object:  View [dbo].[Wierzytelnosci_z_wplatami]    Script Date: 2019-04-28 12:50:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[Wierzytelnosci_z_wplatami]
AS
SELECT        dbo.wierzytelnosci.ID_wierzytelnosci, dbo.wierzytelnosci.Rodzaj, SUM(dbo.wplaty.Kwota) AS Suma_wplat
FROM            dbo.wierzytelnosci LEFT OUTER JOIN
                         dbo.wplaty ON dbo.wplaty.ID_wierzytelnosci = dbo.wierzytelnosci.ID_wierzytelnosci
GROUP BY dbo.wierzytelnosci.ID_wierzytelnosci, dbo.wierzytelnosci.Rodzaj

GO
/****** Object:  View [dbo].[Wierzytelnosci_z_wplatami2]    Script Date: 2019-04-28 12:50:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[Wierzytelnosci_z_wplatami2]
AS
SELECT        dbo.wierzytelnosci.ID_wierzytelnosci, dbo.wierzytelnosci.Rodzaj, SUM(dbo.wplaty.Kwota) AS Suma_wplat
FROM            dbo.wierzytelnosci LEFT OUTER JOIN
                         dbo.wplaty ON dbo.wplaty.ID_wierzytelnosci = dbo.wierzytelnosci.ID_wierzytelnosci
GROUP BY dbo.wierzytelnosci.ID_wierzytelnosci, dbo.wierzytelnosci.Rodzaj
HAVING        (SUM(dbo.wplaty.Kwota) = 0) OR
                         (SUM(dbo.wplaty.Kwota) IS NULL)

GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [IX_adres_dluznika]    Script Date: 2019-04-28 12:50:32 ******/
CREATE NONCLUSTERED INDEX [IX_adres_dluznika] ON [dbo].[adres_dluznika]
(
	[miasto] ASC,
	[ulica] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [IX_adres_wierzyciela]    Script Date: 2019-04-28 12:50:32 ******/
CREATE NONCLUSTERED INDEX [IX_adres_wierzyciela] ON [dbo].[adres_wierzyciela]
(
	[Miasto] ASC,
	[Ulica] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [IX_dluznicy]    Script Date: 2019-04-28 12:50:32 ******/
CREATE NONCLUSTERED INDEX [IX_dluznicy] ON [dbo].[dluznicy]
(
	[nazwisko] ASC,
	[imie] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [IX_dluznicy_1]    Script Date: 2019-04-28 12:50:32 ******/
CREATE NONCLUSTERED INDEX [IX_dluznicy_1] ON [dbo].[dluznicy]
(
	[PESEL] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[adres_dluznika]  WITH CHECK ADD  CONSTRAINT [FK_adres_dluznika_dluznicy] FOREIGN KEY([id_dluznika])
REFERENCES [dbo].[dluznicy] ([ID_dluznika])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[adres_dluznika] CHECK CONSTRAINT [FK_adres_dluznika_dluznicy]
GO
ALTER TABLE [dbo].[dokument]  WITH CHECK ADD  CONSTRAINT [FK_dokument_wierzytelnosci] FOREIGN KEY([ID_wierzytelnosci])
REFERENCES [dbo].[wierzytelnosci] ([ID_wierzytelnosci])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[dokument] CHECK CONSTRAINT [FK_dokument_wierzytelnosci]
GO
ALTER TABLE [dbo].[powiazania]  WITH CHECK ADD  CONSTRAINT [FK_powiazania_dluznicy] FOREIGN KEY([ID_dluznika])
REFERENCES [dbo].[dluznicy] ([ID_dluznika])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[powiazania] CHECK CONSTRAINT [FK_powiazania_dluznicy]
GO
ALTER TABLE [dbo].[powiazania]  WITH CHECK ADD  CONSTRAINT [FK_powiazania_wierzytelnosci] FOREIGN KEY([ID_wierzytelnosci])
REFERENCES [dbo].[wierzytelnosci] ([ID_wierzytelnosci])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[powiazania] CHECK CONSTRAINT [FK_powiazania_wierzytelnosci]
GO
ALTER TABLE [dbo].[wierzyciele]  WITH CHECK ADD  CONSTRAINT [FK_wierzyciele_adres_wierzyciela] FOREIGN KEY([ID_adres_wierzyciela])
REFERENCES [dbo].[adres_wierzyciela] ([ID_adres_wierzyciela])
GO
ALTER TABLE [dbo].[wierzyciele] CHECK CONSTRAINT [FK_wierzyciele_adres_wierzyciela]
GO
ALTER TABLE [dbo].[wplaty]  WITH CHECK ADD  CONSTRAINT [FK_wplaty_wierzytelnosci] FOREIGN KEY([ID_wierzytelnosci])
REFERENCES [dbo].[wierzytelnosci] ([ID_wierzytelnosci])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[wplaty] CHECK CONSTRAINT [FK_wplaty_wierzytelnosci]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[28] 4[9] 2[43] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "wierzyciele"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 135
               Right = 233
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "wierzytelnosci"
            Begin Extent = 
               Top = 6
               Left = 271
               Bottom = 135
               Right = 465
            End
            DisplayFlags = 280
            TopColumn = 4
         End
         Begin Table = "wplaty"
            Begin Extent = 
               Top = 6
               Left = 503
               Bottom = 135
               Right = 682
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
      Begin ColumnWidths = 9
         Width = 284
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 12
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'Wierzyciele_wierzytelnosci_wplaty'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'Wierzyciele_wierzytelnosci_wplaty'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[31] 4[4] 2[45] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "wplaty"
            Begin Extent = 
               Top = 17
               Left = 668
               Bottom = 146
               Right = 847
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "wierzytelnosci"
            Begin Extent = 
               Top = 15
               Left = 456
               Bottom = 144
               Right = 650
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "powiazania"
            Begin Extent = 
               Top = 29
               Left = 250
               Bottom = 141
               Right = 429
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "dluznicy"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 135
               Right = 208
            End
            DisplayFlags = 280
            TopColumn = 1
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
      Begin ColumnWidths = 9
         Width = 284
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 12
         Column = 2835
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
    ' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'Wierzytelnosci_dluznicy_wplaty'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane2', @value=N'  End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'Wierzytelnosci_dluznicy_wplaty'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=2 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'Wierzytelnosci_dluznicy_wplaty'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "wierzytelnosci"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 135
               Right = 248
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "wplaty"
            Begin Extent = 
               Top = 138
               Left = 38
               Bottom = 267
               Right = 233
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
      Begin ColumnWidths = 9
         Width = 284
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 12
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'Wierzytelnosci_z_wplatami'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'Wierzytelnosci_z_wplatami'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[41] 4[21] 2[15] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "wierzytelnosci"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 135
               Right = 248
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "wplaty"
            Begin Extent = 
               Top = 138
               Left = 38
               Bottom = 267
               Right = 233
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
      Begin ColumnWidths = 9
         Width = 284
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 12
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'Wierzytelnosci_z_wplatami2'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'Wierzytelnosci_z_wplatami2'
GO
USE [master]
GO
ALTER DATABASE [wierzytelnosci] SET  READ_WRITE 
GO
